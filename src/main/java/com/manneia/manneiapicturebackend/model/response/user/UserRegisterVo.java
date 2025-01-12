package com.manneia.manneiapicturebackend.model.response.user;

import com.manneia.manneiapicturebackend.model.response.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author luokaixuan
 * @description 用户注册返回对象
 * @created 2025/1/11 20:38
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserRegisterVo extends BaseVo implements Serializable {

    private static final long serialVersionUID = 8915255758366846752L;

    private Long id;

}
