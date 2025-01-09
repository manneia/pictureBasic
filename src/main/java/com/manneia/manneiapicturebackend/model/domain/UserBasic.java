package com.manneia.manneiapicturebackend.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户基础信息表
 * @author lkx
 * @TableName user_basic
 */
@TableName(value ="user_basic")
@Data
public class UserBasic implements Serializable {
    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户账号
     */
    @TableField(value = "userAccount")
    private String userAccount;

    /**
     * 用户密码
     */
    @TableField(value = "userPassword")
    private String userPassword;

    /**
     * 用户姓名
     */
    @TableField(value = "userName")
    private String userName;

    /**
     * 用户头像
     */
    @TableField(value = "userAvatar")
    private String userAvatar;

    /**
     * 用户简介
     */
    @TableField(value = "userProfile")
    private String userProfile;

    /**
     * 用户角色: user/admin
     */
    @TableField(value = "userRole")
    private String userRole;

    /**
     * 会员过期时间
     */
    @TableField(value = "vipExpireTime")
    private Date vipExpireTime;

    /**
     * 会员兑换码
     */
    @TableField(value = "vipCode")
    private String vipCode;

    /**
     * 会员编号
     */
    @TableField(value = "vipNumber")
    private Long vipNumber;

    /**
     * 分享码
     */
    @TableField(value = "shareCode")
    private String shareCode;

    /**
     * 邀请用户 id
     */
    @TableField(value = "inviteUser")
    private Long inviteUser;

    /**
     * 修改时间
     */
    @TableField(value = "editTime")
    private Date editTime;

    /**
     * 创建时间
     */
    @TableField(value = "createTime")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "updateTime")
    private Date updateTime;

    /**
     * 是否删除: 0-未删除, 1-已删除
     */
    @TableField(value = "isDelete")
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}