package com.chen.mmall.dao;

import com.chen.mmall.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    int deleteByShippingIdUserId(@Param("shippingId") Integer shippingId, @Param("userId") Integer userId);

    int updateByShipping(Shipping shipping);

    Shipping selectByShippingId(@Param("shippingId") Integer shippingId, @Param("userId") Integer userId);

    List<Shipping> selectByuserId(@Param("userId") Integer userId);

}