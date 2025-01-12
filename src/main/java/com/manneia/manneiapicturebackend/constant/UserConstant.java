package com.manneia.manneiapicturebackend.constant;

import lombok.Data;

/**
 * @author luokaixuan
 * @description com.manneia.manneiapicturebackend.constant
 * @created 2025/1/11 22:04
 */
@Data
public class UserConstant {

    public static final String USER_LOGIN_STATE = "USER_LOGIN_STATE";
    public static final String DEFAULT_ROLE = "user";
    public static final String ADMIN_ROLE = "admin";

    // 添加私有构造函数以防止实例化
    private UserConstant() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated.");
    }
}
