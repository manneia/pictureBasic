package com.manneia.manneiapicturebackend.model.request.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author luokaixuan
 * @description 用户更新请求体
 * @created 2025/1/12 15:21
 */
@Data
public class UserUpdateRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin
     */
    private String userRole;

    private static final long serialVersionUID = 1L;
}
