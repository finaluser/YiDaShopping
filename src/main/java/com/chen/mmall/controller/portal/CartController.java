package com.chen.mmall.controller.portal;

import com.chen.mmall.common.Const;
import com.chen.mmall.common.ResponseCode;
import com.chen.mmall.common.ServerResponse;
import com.chen.mmall.pojo.User;
import com.chen.mmall.service.ICartService;
import com.chen.mmall.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @program: mmall
 * @description:
 * @author: Mr.Wang
 * @create: 2020-02-18 12:59
 **/
@RequestMapping("/cart/")
@RestController
public class CartController {

    @Autowired
    private ICartService iCartService;

    /**
     * 加入购物车
     *
     * @param session
     * @param count     加入购物车的产品数量
     * @param productId 产品ID
     * @return
     */
    @GetMapping("/add")
    public ServerResponse<CartVO> add(HttpSession session, Integer count, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.add(user.getId(), productId, count);
    }

    /**
     * 更新购物车中某个产品的数量
     *
     * @param session
     * @param count     加入购物车的产品数量
     * @param productId 产品ID
     * @return
     */
    @GetMapping("/update")
    public ServerResponse<CartVO> update(HttpSession session, Integer count, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.update(user.getId(), productId, count);
    }

    /**
     * 购物车中删除产品
     *
     * @param session
     * @param productIds 产品的ID,多个
     * @return
     */
    @GetMapping("/delete_product")
    public ServerResponse<CartVO> deleteProduct(HttpSession session, String productIds) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.deleteProduct(user.getId(), productIds);
    }


    /**
     * 查询购物车中所有的商品
     *
     * @param session
     * @return
     */
    @GetMapping("/list")
    public ServerResponse<CartVO> list(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.list(user.getId());
    }

    /**
     * 购物车中的物品全选
     *
     * @param session
     * @return
     */
    @GetMapping("/select_all")
    public ServerResponse<CartVO> selectAll(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(), null, Const.Cart.CHECKED);
    }

    /**
     * 购物车中的物品全反选
     *
     * @param session
     * @return
     */
    @GetMapping("/un_select_all")
    public ServerResponse<CartVO> unSelectAll(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(), null, Const.Cart.UNCHECK);
    }


    /**
     * 单独选择购物车中的某个产品
     *
     * @param session
     * @param productId 产品ID
     * @return
     */
    @GetMapping("/select")
    public ServerResponse<CartVO> select(HttpSession session, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(), productId, Const.Cart.CHECKED);
    }

    /**
     * 取消选择购物车中的某个产品
     *
     * @param session
     * @param productId 产品ID
     * @return
     */
    @GetMapping("/un_select")
    public ServerResponse<CartVO> unSelect(HttpSession session, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(), productId, Const.Cart.UNCHECK);
    }

    /**
     * 获取购物车中产品的数量
     *
     * @param session
     * @return0
     */
    @GetMapping("/get_cart_product_count")
    public ServerResponse<Integer> getCartProductCount(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createBySuccess(0);
        }
        return iCartService.getCartProductCount(user.getId());
    }


}
