package com.chen.mmall.service;

import com.chen.mmall.common.ServerResponse;
import com.chen.mmall.pojo.Shipping;
import com.github.pagehelper.PageInfo;

public interface IShippingService {
    ServerResponse add(Integer userId, Shipping shipping);

    ServerResponse<String> del(Integer userId, Integer shippingId);

    ServerResponse<String> update(Integer userId, Shipping shipping);

    ServerResponse<Shipping> select(Integer userId, Integer shippingId);

    ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize);
}
