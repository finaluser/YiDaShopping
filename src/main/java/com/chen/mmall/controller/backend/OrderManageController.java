package com.chen.mmall.controller.backend;

import com.chen.mmall.common.Const;
import com.chen.mmall.common.ResponseCode;
import com.chen.mmall.common.ServerResponse;
import com.chen.mmall.pojo.User;
import com.chen.mmall.service.IOrderService;
import com.chen.mmall.service.IUserService;
import com.chen.mmall.vo.OrderVO;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @program: mmall
 * @description: 后台订单管理Controller
 * @author: Mr.Wang
 * @create: 2020-03-02 13:53
 **/
@RestController
@RequestMapping("/manage/order")
public class OrderManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IOrderService iOrderService;


    /**
     * 订单列表
     *
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/orderlist")
    public ServerResponse<PageInfo> orderList(HttpSession session,
                                              @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "没有登陆,请登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iOrderService.manageList(pageNum, pageSize);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    /**
     * 订单详情
     *
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping("/detail")
    public ServerResponse<OrderVO> orderDetail(HttpSession session, Long orderNo) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "没有登陆,请登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iOrderService.manageDetail(orderNo);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    /**
     * 搜索订单
     *
     * @param session
     * @param orderNo
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/search")
    public ServerResponse<PageInfo> searchOrder(HttpSession session, Long orderNo,
                                                @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "没有登陆,请登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iOrderService.manageSearch(orderNo, pageNum, pageSize);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    /**
     * 商品发货
     *
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping("/send_goods")
    public ServerResponse<String> orderSendGoods(HttpSession session, Long orderNo) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "没有登陆,请登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iOrderService.manageSendGoods(orderNo);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

}
