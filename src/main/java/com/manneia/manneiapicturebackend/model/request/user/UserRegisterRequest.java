package com.manneia.manneiapicturebackend.model.request.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author luokaixuan
 * @description 用户注册请求类
 * @created 2025/1/9 14:07
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 1474361119481593454L;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 确认密码
     */
    private String checkPassword;
}
