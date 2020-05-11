package com.chen.mmall.controller.portal;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.chen.mmall.common.Const;
import com.chen.mmall.common.ResponseCode;
import com.chen.mmall.common.ServerResponse;
import com.chen.mmall.pojo.User;
import com.chen.mmall.service.IOrderService;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;

/**
 * @program: mmall
 * @description: 订单Controller
 * @author: Mr.Wang
 * @create: 2020-02-20 10:36
 **/
@RestController
@RequestMapping("/order")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private IOrderService iOrderService;

    /**
     * 获取购物车中已经选中的商品详情
     *
     * @param session
     * @param orderNo 订单号
     * @return
     */
    @RequestMapping("/get_order_cart_product")
    public ServerResponse getOrderCartProduct(HttpSession session, Long orderNo) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.getOrderCartProduct(user.getId());
    }

    /**
     * 订单详情的查看
     *
     * @param session
     * @param orderNo 订单号
     * @return
     */
    @RequestMapping("/detail")
    public ServerResponse detail(HttpSession session, Long orderNo) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.getOrderDetail(user.getId(), orderNo);
    }

    /**
     * 用户端的订单列表
     *
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/list")
    public ServerResponse list(HttpSession session,
                               @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.getOrderList(user.getId(), pageNum, pageSize);
    }


    /**
     * 取消订单功能
     *
     * @param session 用户会话
     * @param orderNo 订单号码
     * @return
     */
    @RequestMapping("/cancle")
    public ServerResponse cancle(HttpSession session, Long orderNo) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.cancle(user.getId(), orderNo);
    }

    /**
     * 创建订单
     *
     * @param session
     * @param shippingId 发货地址ID
     * @return
     */
    @RequestMapping("/create")
    public ServerResponse create(HttpSession session, Integer shippingId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.createOrder(user.getId(), shippingId);
    }

    /**
     * 订单支付
     *
     * @param session
     * @param orderNo 订单号
     * @param request HTTP请求
     * @return
     */
    @RequestMapping("/pay")
    public ServerResponse pay(HttpSession session, Long orderNo, HttpServletRequest request) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }

        String path = request.getSession().getServletContext().getRealPath("upload");
        return iOrderService.pay(user.getId(), path, orderNo);
    }

    /**
     * 支付宝回调接口
     *
     * @param request HTTP请求
     * @return
     */
    @RequestMapping(value = "/alipay_callback")
    public Object alipayCallback(HttpServletRequest request) {
        Map<String, String> params = Maps.newHashMap();

        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {

                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        logger.info("支付宝回调,sign:{},trade_status:{},参数:{}", params.get("sign"), params.get("trade_status"), params.toString(), Configs.getSignType());
        //验证回调的正确性,并且避免重复通知
        params.remove("sign_type");

        try {
            boolean alipayRSACheckedV2 = AlipaySignature.rsaCheckV2(params, Configs.getAlipayPublicKey(), "utf-8", Configs.getSignType());
            if (!alipayRSACheckedV2) {
                return ServerResponse.createByErrorMessage("非法请求，验证不通过。");
            }
        } catch (AlipayApiException e) {
            logger.error("支付宝验证回调异常：" + e);
        }
        ServerResponse serverResponse = iOrderService.aliCallBack(params);
        if (serverResponse.isSuccess()) {
            return Const.AlipayCallBack.RESPONSE_SUCCESS;
        }
        return Const.AlipayCallBack.RESPONSE_FAILED;
    }

    /**
     * 查询订单的支付状态
     *
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping("/query_order_pay_status")
    public ServerResponse<Boolean> queryOrderPayStatus(HttpSession session, Long orderNo) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        ServerResponse serverResponse = iOrderService.queryOrderPayStatus(user.getId(), orderNo);
        if (serverResponse.isSuccess()) {
            return ServerResponse.createBySuccess(true);
        }
        return ServerResponse.createBySuccess(false);
    }


}
