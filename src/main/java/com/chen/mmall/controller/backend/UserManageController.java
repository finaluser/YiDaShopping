package com.chen.mmall.controller.backend;

import com.chen.mmall.common.Const;
import com.chen.mmall.common.ServerResponse;
import com.chen.mmall.pojo.User;
import com.chen.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @program: mmall
 * @description: 用户后台管理
 * @author: Mr.Wang
 * @create: 2020-02-04 21:18
 **/
@RestController
@RequestMapping("/manage/user/")
public class UserManageController {

    @Autowired
    private IUserService iUserService;

    @PostMapping(value = "login")
    public ServerResponse<User> login(String username, String password, HttpSession session) {
        ServerResponse<User> response = iUserService.login(username, password);
        if (response.isSuccess()) {
            User user = response.getData();
            if (user.getRole() == Const.Role.ROLE_ADMIN) {
                session.setAttribute(Const.CURRENT_USER, user);
                return response;
            } else {
                return ServerResponse.createByErrorMessage("不是管理员");
            }
        }
        return response;
    }
}
