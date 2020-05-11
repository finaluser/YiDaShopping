package com.chen.mmall.dao;

import com.chen.mmall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 检查用户名是否存在
     *
     * @param username
     * @return
     */
    int checkUsername(String username);

    /**
     * 检查用户是否登录
     *
     * @param username
     * @param password
     * @return
     */
    User selectLogin(@Param("username") String username, @Param("password") String password);

    /**
     * 检查邮箱是否存在
     *
     * @param email
     * @return
     */
    int checkEmail(String email);

    /**
     * 根据用户名查询问题
     *
     * @param username
     * @return
     */
    String selectQuestionByUsername(String username);

    /**
     * 查询问题的答案
     *
     * @param username
     * @param question
     * @param answer
     * @return
     */
    int checkAnswer(@Param("username") String username, @Param("question") String question, @Param("answer") String answer);

    /**
     * 根据用户名更新密码
     *
     * @param username
     * @param passwordNew
     * @return
     */
    int updatePasswordByUsername(@Param("username") String username, @Param("passwordNew") String passwordNew);

    /**
     * 根据Id查询用户的旧的密码
     *
     * @param password
     * @param userId
     * @return
     */
    int checkPassword(@Param("password") String password, @Param("userId") Integer userId);

    /**
     * 根据用户ID检查邮箱是否存在
     *
     * @param email
     * @param userId
     * @return
     */
    int checkEmailByUserId(@Param("email") String email, @Param("userId") Integer userId);
}