package com.chen.mmall.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @program: mmall
 * @description:
 * @author: Mr.Wang
 * @create: 2020-02-18 13:17
 **/
public class CartVO {
    List<CartProductVO> cartProductVOList;
    private BigDecimal cartTotalPrice;
    private boolean allChecked; // 购物车中的产品是否已经都勾选
    private String imageHost;

    public List<CartProductVO> getCartProductVOList() {
        return cartProductVOList;
    }

    public void setCartProductVOList(List<CartProductVO> cartProductVOList) {
        this.cartProductVOList = cartProductVOList;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    public boolean isAllChecked() {
        return allChecked;
    }

    public void setAllChecked(boolean allChecked) {
        this.allChecked = allChecked;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }
}
