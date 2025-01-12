package com.manneia.manneiapicturebackend.model.response.user;

import com.manneia.manneiapicturebackend.model.response.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author luokaixuan
 * @description com.manneia.manneiapicturebackend.model.response.user
 * @created 2025/1/12 15:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserAddVo extends BaseVo implements Serializable {

    private static final long serialVersionUID = 8915255758366846752L;

    private Long id;
}
