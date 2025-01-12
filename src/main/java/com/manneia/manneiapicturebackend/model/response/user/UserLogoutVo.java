package com.manneia.manneiapicturebackend.model.response.user;

import com.manneia.manneiapicturebackend.model.response.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author luokaixuan
 * @description com.manneia.manneiapicturebackend.model.response.user
 * @created 2025/1/11 20:49
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserLogoutVo extends BaseVo implements Serializable {

    private static final long serialVersionUID = -3430951445563474093L;

    private Boolean logOutIsSuccess;
}
