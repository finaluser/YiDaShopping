package com.chen.mmall.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @program: mmall
 * @description:
 * @author: Mr.Wang
 * @create: 2020-03-02 12:46
 **/
public class OrderProductVO {
    private List<OrderItemVO> orderItemVOList;
    private BigDecimal productTotalPrice;
    private String imageHost;

    public List<OrderItemVO> getOrderItemVOList() {
        return orderItemVOList;
    }

    public void setOrderItemVOList(List<OrderItemVO> orderItemVOList) {
        this.orderItemVOList = orderItemVOList;
    }

    public BigDecimal getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(BigDecimal productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }
}
