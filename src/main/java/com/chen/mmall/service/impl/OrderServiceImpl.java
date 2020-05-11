package com.chen.mmall.service.impl;

import com.alipay.api.AlipayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.chen.mmall.common.Const;
import com.chen.mmall.common.ServerResponse;
import com.chen.mmall.dao.*;
import com.chen.mmall.pojo.*;
import com.chen.mmall.service.IOrderService;
import com.chen.mmall.util.BigDecimalUtil;
import com.chen.mmall.util.DateTimeUtil;
import com.chen.mmall.util.FTPUtil;
import com.chen.mmall.util.PropertiesUtil;
import com.chen.mmall.vo.OrderItemVO;
import com.chen.mmall.vo.OrderProductVO;
import com.chen.mmall.vo.OrderVO;
import com.chen.mmall.vo.ShippingVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @program: mmall
 * @description:
 * @author: Mr.Wang
 * @create: 2020-02-20 10:40
 **/
@Service("iOrderService")
public class OrderServiceImpl implements IOrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private PayInfoMapper payInfoMapper;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ShippingMapper shippingMapper;


    public ServerResponse<PageInfo> getOrderList(Integer userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.selectByUserId(userId);
        List<OrderVO> orderVOList = assembleOrderVOList(orderList, userId);
        PageInfo pageResult = new PageInfo(orderList);
        pageResult.setList(orderVOList);
        return ServerResponse.createBySuccess(pageResult);

    }

    public ServerResponse<OrderVO> getOrderDetail(Integer userId, Long orderNo) {
        Order order = orderMapper.selectByUserIdAndOrderNum(userId, orderNo);
        if (order != null) {
            List<OrderItem> orderItemList = orderItemMapper.getByOrderNoUserId(orderNo, userId);
            OrderVO orderVO = assembleOrderVO(order, orderItemList);
            return ServerResponse.createBySuccess(orderVO);
        }
        return ServerResponse.createByErrorMessage("没有找到该订单");
    }

    public ServerResponse getOrderCartProduct(Integer userId) {
        OrderProductVO orderProductVO = new OrderProductVO();
        List<Cart> cartList = cartMapper.selectCheckedByUserId(userId);
        ServerResponse cartOrderItem = this.getCartOrderItem(userId, cartList);
        if (!cartOrderItem.isSuccess()) {
            return cartOrderItem;
        }
        List<OrderItem> orderItemList = (List<OrderItem>) cartOrderItem.getData();
        List<OrderItemVO> orderItemVOList = Lists.newArrayList();
        BigDecimal payment = new BigDecimal("0");
        for (OrderItem orderItem : orderItemList) {
            payment = BigDecimalUtil.add(payment.doubleValue(), orderItem.getTotalPrice().doubleValue());
            orderItemVOList.add(assembleOrderItemVO(orderItem));
        }
        orderProductVO.setProductTotalPrice(payment);
        orderProductVO.setOrderItemVOList(orderItemVOList);
        orderProductVO.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        return ServerResponse.createBySuccess(orderProductVO);
    }

    public ServerResponse<String> cancle(Integer userId, Long orderNo) {
        Order order = orderMapper.selectByUserIdAndOrderNum(userId, orderNo);
        if (order == null) {
            return ServerResponse.createByErrorMessage("该用户的订单不存在");
        }
        if (order.getStatus() != Const.OrderStatusEnum.NO_PAY.getCode()) {
            // TODO 支付宝退款功能
            return ServerResponse.createByErrorMessage("已经付款,无法取消订单");
        }

        Order updateOrder = new Order();
        updateOrder.setId(order.getId());
        updateOrder.setStatus(Const.OrderStatusEnum.CANCELED.getCode());
        int row = orderMapper.updateByPrimaryKeySelective(updateOrder);
        if (row > 0) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();

    }


    public ServerResponse createOrder(Integer userId, Integer shippingId) {
        // 从购物车中获取数据
        List<Cart> cartList = cartMapper.selectCheckedByUserId(userId);
        // 计算这个订单的总价
        ServerResponse serverResponse = getCartOrderItem(userId, cartList);
        if (!serverResponse.isSuccess()) {
            return ServerResponse.createByErrorMessage("");
        }
        List<OrderItem> orderItemList = (List<OrderItem>) serverResponse.getData();
        //订单总价
        BigDecimal payment = this.getOrderTotalPrice(orderItemList);
        // 生成订单
        Order order = this.assembleOrder(userId, shippingId, payment);
        if (order == null) {
            return ServerResponse.createByErrorMessage("生成订单错误");
        }
        if (CollectionUtils.isEmpty(orderItemList)) {
            return ServerResponse.createByErrorMessage("购物车为空");
        }
        for (OrderItem orderItem : orderItemList
        ) {
            orderItem.setOrderNo(order.getOrderNo());
        }

        //MyBatis 批量插入
        orderItemMapper.batchInsert(orderItemList);
        //生成订单成功,减少产品库存,清空购物车
        this.reduceProductStock(orderItemList);
        this.cleanCart(cartList);

        //返回给前端数据
        OrderVO orderVO = this.assembleOrderVO(order, orderItemList);
        return ServerResponse.createBySuccess(orderVO);
    }

    /**
     * 组装OrderVO
     *
     * @param order
     * @param orderItemList
     * @return
     */
    private OrderVO assembleOrderVO(Order order, List<OrderItem> orderItemList) {
        OrderVO orderVO = new OrderVO();
        orderVO.setOrderNo(order.getOrderNo());
        orderVO.setPayment(order.getPayment());
        orderVO.setPaymentType(order.getPaymentType());
        orderVO.setPaymentTypeDesc(Const.PaymentTypeEnum.codeOf(order.getPaymentType()).getValue());
        orderVO.setPostage(order.getPostage());
        orderVO.setStatus(order.getStatus());
        orderVO.setStatusDesc(Const.OrderStatusEnum.codeOf(order.getStatus()).getValue());
        orderVO.setShippingId(order.getShippingId());
        Shipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());
        if (shipping != null) {
            orderVO.setReceiverName(shipping.getReceiverName());
            orderVO.setShippingVO(assembleShippingVO(shipping));
        }

        orderVO.setPaymentTime(DateTimeUtil.dateToStr(order.getPaymentTime()));
        orderVO.setSendTime(DateTimeUtil.dateToStr(order.getSendTime()));
        orderVO.setEndTime(DateTimeUtil.dateToStr(order.getEndTime()));
        orderVO.setCloseTime(DateTimeUtil.dateToStr(order.getCloseTime()));
        orderVO.setCreateTime(DateTimeUtil.dateToStr(order.getCreateTime()));

        orderVO.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));

        List<OrderItemVO> orderItemVOList = Lists.newArrayList();

        for (OrderItem orderItem : orderItemList
        ) {
            OrderItemVO orderItemVO = assembleOrderItemVO(orderItem);
            orderItemVOList.add(orderItemVO);
        }

        orderVO.setOrderItemVOList(orderItemVOList);
        return orderVO;
    }

    private List<OrderVO> assembleOrderVOList(List<Order> orderList, Integer userId) {
        List<OrderVO> orderVOList = Lists.newArrayList();
        for (Order order : orderList) {
            List<OrderItem> orderItemList = Lists.newArrayList();
            if (userId == null) {
                orderItemList = orderItemMapper.getByOrderNo(order.getOrderNo());
            } else {
                // 普通用户
                orderItemList = orderItemMapper.getByOrderNoUserId(order.getOrderNo(), userId);
            }
            OrderVO orderVO = assembleOrderVO(order, orderItemList);
            orderVOList.add(orderVO);
        }
        return orderVOList;
    }

    private OrderItemVO assembleOrderItemVO(OrderItem orderItem) {
        OrderItemVO orderItemVO = new OrderItemVO();
        orderItemVO.setTotalPrice(orderItem.getTotalPrice());
        orderItemVO.setCreateTime(DateTimeUtil.dateToStr(orderItem.getCreateTime()));
        orderItemVO.setOrderNo(orderItem.getOrderNo());
        orderItemVO.setProductId(orderItem.getProductId());
        orderItemVO.setProductImage(orderItem.getProductImage());
        orderItemVO.setProductName(orderItem.getProductName());
        orderItemVO.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
        orderItemVO.setTotalPrice(orderItem.getTotalPrice());
        orderItemVO.setQuantity(orderItem.getQuantity());
        return orderItemVO;
    }

    private ShippingVO assembleShippingVO(Shipping shipping) {
        ShippingVO shippingVO = new ShippingVO();
        shippingVO.setReceiverAddress(shipping.getReceiverAddress());
        shippingVO.setReceiverCity(shipping.getReceiverCity());
        shippingVO.setReceiverDistrict(shipping.getReceiverDistrict());
        shippingVO.setReceiverMobile(shipping.getReceiverMobile());
        shippingVO.setReceiverPhone(shipping.getReceiverPhone());
        shippingVO.setReceiverZip(shipping.getReceiverZip());
        shippingVO.setReceiverName(shipping.getReceiverName());
        shippingVO.setReceiverProvince(shipping.getReceiverProvince());
        return shippingVO;
    }


    private void cleanCart(List<Cart> cartList) {
        for (Cart cart : cartList
        ) {
            cartMapper.deleteByPrimaryKey(cart.getId());
        }
    }

    private void reduceProductStock(List<OrderItem> orderItemList) {
        for (OrderItem orderItem : orderItemList) {
            Product product = productMapper.selectByPrimaryKey(orderItem.getProductId());
            product.setStock(product.getStock() - orderItem.getQuantity());
            productMapper.updateByPrimaryKeySelective(product);
        }
    }


    private Order assembleOrder(Integer userId, Integer shippingId, BigDecimal payment) {
        Order order = new Order();
        long orderNo = this.generateOrderNo();
        order.setOrderNo(orderNo);
        order.setStatus(Const.OrderStatusEnum.NO_PAY.getCode());
        // 邮费
        order.setPostage(0);
        order.setPaymentType(Const.PaymentTypeEnum.ONLINE_PAY.getCode());
        order.setPayment(payment);
        order.setUserId(userId);
        order.setShippingId(shippingId);
        int rowCount = orderMapper.insert(order);
        if (rowCount > 0) {
            return order;
        }
        return null;
    }

    // 订单号的生成规则非常重要
    private long generateOrderNo() {
        long currentTime = System.currentTimeMillis();
        return currentTime + new Random().nextInt(100);
    }

    private BigDecimal getOrderTotalPrice(List<OrderItem> orderItemList) {
        BigDecimal payment = new BigDecimal("0");
        for (OrderItem orderItem : orderItemList
        ) {
            payment = BigDecimalUtil.add(payment.doubleValue(), orderItem.getTotalPrice().doubleValue());
        }
        return payment;
    }

    /**
     * 获取购物车中的有效商品
     *
     * @param userId
     * @param cartList
     * @return
     */
    private ServerResponse getCartOrderItem(Integer userId, List<Cart> cartList) {
        List<OrderItem> orderItemList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(cartList)) {
            return ServerResponse.createByErrorMessage("购物车为空");
        }

        for (Cart cartItem : cartList
        ) {
            OrderItem orderItem = new OrderItem();
            Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
            // 校验产品的状态是否是在线售卖
            if (Const.ProductStatusEnum.ON_SALE.getCode() != product.getStatus()) {
                return ServerResponse.createByErrorMessage("产品" + product.getName() + "不是在线售卖状态");
            }
            // 判断购物车中的产品数量是否大于库存数量
            if (cartItem.getQuantity() > product.getStock()) {
                return ServerResponse.createByErrorMessage("产品" + product.getName() + "库存不足");
            }
            orderItem.setUserId(userId);
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setProductImage(product.getMainImage());
            orderItem.setCurrentUnitPrice(product.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), cartItem.getQuantity()));
            orderItemList.add(orderItem);
        }
        return ServerResponse.createBySuccess(orderItemList);
    }

    /**
     * @param userId  用户ID
     * @param path    二维码路径
     * @param orderNo 订单号码
     * @return
     */
    public ServerResponse pay(Integer userId, String path, Long orderNo) {
        Map<String, String> resultMap = Maps.newHashMap();
        //根据用户ID和订单ID获取订单
        Order order = orderMapper.selectByUserIdAndOrderNum(userId, orderNo);
        if (order == null) {
            return ServerResponse.createByErrorMessage("用户没有此订单");
        }
        resultMap.put("orderNo", String.valueOf(order.getOrderNo()));

        // TODO 扫码支付二维码生成


        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = order.getOrderNo().toString();

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = new StringBuilder().append("MMall扫码支付,订单号:").append(outTradeNo).toString();

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = order.getPayment().toString();

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = new StringBuilder().append("订单").append(outTradeNo).append("购买商品共").append(totalAmount).append("元").toString();

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "test_store_id";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");

        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();


        List<OrderItem> orderItemList = orderItemMapper.getByOrderNoUserId(orderNo, userId);
        for (OrderItem orderItem : orderItemList
        ) {
            GoodsDetail goods = GoodsDetail.newInstance(orderItem.getProductId().toString(), orderItem.getProductName(),
                    BigDecimalUtil.mul(orderItem.getCurrentUnitPrice().doubleValue(), new Double(100).doubleValue()).longValue(),
                    orderItem.getQuantity());
            goodsDetailList.add(goods);
        }
        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                .setNotifyUrl(PropertiesUtil.getProperty("alipay.callback.url"))//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
                .setGoodsDetailList(goodsDetailList);

        Configs.init("zfbinfo.properties");

        /** 使用Configs提供的默认参数
         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        // 支付宝当面付2.0服务
        AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();

        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                logger.info("支付宝预下单成功: )");
                AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);

                //支付宝预下单成功之后就创建一个二维码

                File folder = new File(path);
                if (!folder.exists()) {
                    folder.setWritable(true);
                    folder.mkdirs();
                }

                // 需要修改为运行机器上的路径
                String qrPath = String.format(path + "/qr-%s.png", response.getOutTradeNo());
                String qrFileName = String.format("qr-%s.png", response.getOutTradeNo());
                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, qrPath);

                File targetFile = new File(path, qrFileName);
                try {
                    FTPUtil.uploadFile(Lists.<File>newArrayList(targetFile));
                } catch (IOException e) {
                    logger.error("上传二维码异常", e);
                }

                logger.info("qrPath:" + qrPath);
                String qeUrl = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFile.getName();
                resultMap.put("qrUrl", qeUrl);
                return ServerResponse.createBySuccess(resultMap);

            case FAILED:
                logger.error("支付宝预下单失败!!!");
                return ServerResponse.createByErrorMessage("支付宝预下单失败!!!");
            case UNKNOWN:
                logger.error("系统异常，预下单状态未知!!!");
                return ServerResponse.createByErrorMessage("系统异常，预下单状态未知!!!");
            default:
                logger.error("不支持的交易状态，交易返回异常!!!");
                return ServerResponse.createByErrorMessage("不支持的交易状态，交易返回异常!!!");
        }
    }

    public ServerResponse aliCallBack(Map<String, String> params) {
        Long orderNo = Long.parseLong(params.get("out_trade_no"));
        String tradeNo = params.get("trade_no");
        String tradeStatus = params.get("trade_status");
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null) {
            return ServerResponse.createByErrorMessage("非本平台的订单，回调忽略");
        }
        if (order.getStatus() >= Const.OrderStatusEnum.PAID.getCode()) {
            return ServerResponse.createBySuccess("支付宝重复调用");
        }

        //已付款
        if (Const.AlipayCallBack.TRADE_STATUS_TRADE_SUCCESS.equals(tradeStatus)) {
            order.setPaymentTime(DateTimeUtil.strToDate(params.get("gmt_payment")));
            order.setStatus(Const.OrderStatusEnum.PAID.getCode());
            orderMapper.updateByPrimaryKeySelective(order);
        }

        PayInfo payInfo = new PayInfo();
        payInfo.setUserId(order.getUserId());
        payInfo.setOrderNo(order.getOrderNo());
        payInfo.setPayPlatform(Const.PayPlatformEnum.ALIPAY.getCode());
        payInfo.setPlatformNumber(tradeNo);
        payInfo.setPlatformStatus(tradeStatus);

        payInfoMapper.insert(payInfo);

        return ServerResponse.createBySuccess();
    }

    public ServerResponse queryOrderPayStatus(Integer userId, Long orderNo) {
        Order order = orderMapper.selectByUserIdAndOrderNum(userId, orderNo);
        if (order == null) {
            return ServerResponse.createByErrorMessage("没有该订单");
        }
        if (order.getStatus() >= Const.OrderStatusEnum.PAID.getCode()) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }


    // 简单打印应答
    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
            logger.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                logger.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                        response.getSubMsg()));
            }
            logger.info("body:" + response.getBody());
        }
    }

    // 后台相关
    public ServerResponse<PageInfo> manageList(int pageNum, int pageSize) {
        // TODO 如果不注释下一行,则会出现查询错误
//        PageHelper.startPage(pageNum,pageSize);
        List<Order> orderList = orderMapper.selectAllOrder();
        List<OrderVO> orderVoList = this.assembleOrderVOList(orderList,null);
        PageInfo pageResult = new PageInfo(orderList);
        pageResult.setList(orderVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    public ServerResponse<OrderVO> manageDetail(Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order != null) {
            List<OrderItem> orderItemList = orderItemMapper.getByOrderNo(orderNo);
            OrderVO orderVO = assembleOrderVO(order, orderItemList);
            return ServerResponse.createBySuccess(orderVO);
        }
        return ServerResponse.createByErrorMessage("订单不存在");
    }

    /**
     * 订单搜素
     *
     * @param orderNo
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ServerResponse<PageInfo> manageSearch(Long orderNo, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order != null) {
            List<OrderItem> orderItemList = orderItemMapper.getByOrderNo(order.getOrderNo());
            OrderVO orderVO = assembleOrderVO(order, orderItemList);
            PageInfo pageResult = new PageInfo(Lists.newArrayList(order));
            pageResult.setList(Lists.newArrayList(orderVO));
            return ServerResponse.createBySuccess(pageResult);
        }
        return ServerResponse.createByErrorMessage("订单不存在");

    }

    /**
     * 订单发货
     *
     * @param orderNo
     * @return
     */
    public ServerResponse<String> manageSendGoods(Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order != null) {
            if (order.getStatus() == Const.OrderStatusEnum.PAID.getCode()) {
                order.setStatus(Const.OrderStatusEnum.SHIPPED.getCode());
                order.setSendTime(new Date());
                orderMapper.updateByPrimaryKeySelective(order);
                return ServerResponse.createBySuccess("发货成功");
            }
        }
        return ServerResponse.createByErrorMessage("订单不存在或未支付");
    }
}
