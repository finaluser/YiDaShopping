package com.chen.mmall.controller.portal;

import com.chen.mmall.common.Const;
import com.chen.mmall.common.ResponseCode;
import com.chen.mmall.common.ServerResponse;
import com.chen.mmall.pojo.User;
import com.chen.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @program: mmall
 * @description:
 * @author: Mr.Wang
 * @create: 2020-02-03 23:08
 **/
@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService iUserService;

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @param session
     * @return
     */
    @PostMapping(value = "/login")
    public ServerResponse<User> login(@RequestBody User user, HttpSession session) {
        ServerResponse<User> response = iUserService.login(user.getUsername(), user.getPassword());
        if (response != null) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    /**
     * 用户登出
     *
     * @param session
     * @return
     */
    @PostMapping(value = "/logout")
    public ServerResponse<String> logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @PostMapping(value = "/register")
    public ServerResponse<String> register(@RequestBody User user) {
        return iUserService.register(user);
    }

    /**
     * 用户注册的时候进行参数校验
     *
     * @param str
     * @param type
     * @return
     */
    @PostMapping(value = "/check_valid")
    public ServerResponse<String> checkValid(String str, String type) {
        return iUserService.checkValid(str, type);
    }

    /**
     * 用户登录后获取用户详情信息
     *
     * @param session
     * @return
     */
    @PostMapping(value = "/get_user_info")
    public ServerResponse<User> getUserInfo(HttpSession session) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser != null) {
            return ServerResponse.createBySuccess(currentUser);
        }
        return ServerResponse.createByErrorMessage("用户未登录无法获取当前用户信息");
    }

    /**
     * 忘记密码:获取用户密码提示问题
     *
     * @param username
     * @return
     */
    @PostMapping(value = "/forget_get_question")
    public ServerResponse<String> forgetGetQuestion(String username) {
        return iUserService.selectQuestion(username);
    }

    /**
     * 忘记密码:提交密码提示问题的答案
     *
     * @param username
     * @param question
     * @param answer
     * @return
     */
    @PostMapping(value = "/forget_check_answer")
    public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer) {
        return iUserService.checkAnswer(username, question, answer);
    }

    /**
     * 忘记密码:重设密码
     *
     * @param username
     * @param passwordNew
     * @param forgetToken 利用密码找回问题找回密码的时候,如果问题回答成功会给客户端返回一个Token
     * @return
     */
    @PostMapping(value = "/forget_rest_password")
    public ServerResponse<String> forgetRestPassword(String username, String passwordNew, String forgetToken) {
        return iUserService.forgetResetPassword(username, passwordNew, forgetToken);
    }

    /**
     * 登录状态的重置密码
     *
     * @param session
     * @param passwordOld
     * @param passwordNew
     * @return
     */
    @PostMapping(value = "/reset_password")
    public ServerResponse<String> resetPassword(HttpSession session, String passwordOld, String passwordNew) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            ServerResponse.createByErrorMessage("用户没有登录");
        }
        return iUserService.resetPassword(passwordOld, passwordNew, currentUser);
    }

    /**
     * 更新用户个人信息
     *
     * @param session
     * @param user
     * @return
     */
    @PostMapping(value = "/update_information")
    public ServerResponse<User> updateInformation(HttpSession session, @RequestBody User user) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response = iUserService.updateInfomation(user);
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    /**
     * 获取用户详情信息
     *
     * @param session
     * @return
     */
    @PostMapping(value = "/get_information")
    public ServerResponse<User> getInformation(HttpSession session) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录");
        }
        return iUserService.getInformation(currentUser.getId());
    }

}
