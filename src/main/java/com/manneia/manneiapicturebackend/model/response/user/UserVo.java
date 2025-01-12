package com.manneia.manneiapicturebackend.model.response.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author luokaixuan
 * @description 用户视图(脱敏)
 * @created 2025/1/11 22:54
 */
@Data
public class UserVo implements Serializable {

    /**
     * 用户 id
     */
    private Long id;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin
     */
    private String userRole;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

}
