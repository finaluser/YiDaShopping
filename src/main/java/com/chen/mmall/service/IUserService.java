package com.chen.mmall.service;


import com.chen.mmall.common.ServerResponse;
import com.chen.mmall.pojo.User;

public interface IUserService {
    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkValid(String str, String type);

    ServerResponse selectQuestion(String username);

    ServerResponse<String> checkAnswer(String username, String question, String answer);

    ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken);

    ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user);

    ServerResponse<User> updateInfomation(User user);

    ServerResponse<User> getInformation(Integer userId);

    ServerResponse checkAdminRole(User user);
}
