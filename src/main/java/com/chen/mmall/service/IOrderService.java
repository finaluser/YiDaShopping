package com.chen.mmall.service;

import com.github.pagehelper.PageInfo;
import com.chen.mmall.common.ServerResponse;
import com.chen.mmall.vo.OrderVO;

import java.util.Map;

public interface IOrderService {

    ServerResponse pay(Integer userId, String path, Long orderNo);

    ServerResponse aliCallBack(Map<String, String> params);

    ServerResponse queryOrderPayStatus(Integer userId, Long orderNo);

    ServerResponse createOrder(Integer userId, Integer shippingId);

    ServerResponse<String> cancle(Integer userId, Long orderNo);

    ServerResponse getOrderCartProduct(Integer userId);

    ServerResponse<OrderVO> getOrderDetail(Integer userId, Long orderNo);

    ServerResponse<PageInfo> getOrderList(Integer userId, int pageNum, int pageSize);

    //后台订单列表
    ServerResponse<PageInfo> manageList(int pageNum, int pageSize);

    ServerResponse<OrderVO> manageDetail(Long orderNo);

    ServerResponse<PageInfo> manageSearch(Long orderNo, int pageNum, int pageSize);

    ServerResponse<String> manageSendGoods(Long orderNo);
}
