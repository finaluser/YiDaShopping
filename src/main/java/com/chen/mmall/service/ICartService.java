package com.chen.mmall.service;

import com.chen.mmall.common.ServerResponse;
import com.chen.mmall.vo.CartVO;

public interface ICartService {

    ServerResponse<CartVO> add(Integer userId, Integer productId, Integer count);

    ServerResponse<CartVO> update(Integer userId, Integer productId, Integer count);

    ServerResponse<CartVO> deleteProduct(Integer userId, String productIds);

    ServerResponse<CartVO> list(Integer userID);

    ServerResponse<CartVO> selectOrUnSelect(Integer userId, Integer productId, Integer checked);

    ServerResponse<Integer> getCartProductCount(Integer userId);
}
