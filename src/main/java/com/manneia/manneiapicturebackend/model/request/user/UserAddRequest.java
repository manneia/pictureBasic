package com.manneia.manneiapicturebackend.model.request.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lkx
 * @description 用户创建请求体
 * @created 2025/1/12 15:21
 */
@Data
public class UserAddRequest implements Serializable {

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户角色: user, admin
     */
    private String userRole;

    private static final long serialVersionUID = 1L;
}
