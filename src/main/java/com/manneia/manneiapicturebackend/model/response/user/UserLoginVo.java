package com.manneia.manneiapicturebackend.model.response.user;

import com.manneia.manneiapicturebackend.model.response.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author luokaixuan
 * @description 用户登录返回对象
 * @created 2025/1/11 20:44
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserLoginVo extends BaseVo implements Serializable {

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

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}
