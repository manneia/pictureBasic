package com.manneia.manneiapicturebackend.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author luokaixuan
 * @description 删除请求参数
 * @created 2024/12/8 18:03
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 7496731335488345766L;

}
