package com.manneia.manneiapicturebackend.model.request.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author luokaixuan
 * @description 用户登录请求类
 * @created 2025/1/9 15:48
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 5754280815485565301L;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户密码
     */
    private String userPassword;
}
