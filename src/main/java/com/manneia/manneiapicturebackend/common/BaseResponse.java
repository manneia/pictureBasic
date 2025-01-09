package com.manneia.manneiapicturebackend.common;

import com.manneia.manneiapicturebackend.constant.ResponseCode;
import lombok.Data;

import java.io.Serializable;

/**
 * @author luokaixuan
 * @description 接口公共响应类
 * @created 2024/12/8 17:35
 */
@Data
@SuppressWarnings("unused")
public class BaseResponse<T> implements Serializable {

    private final String code;

    private T data;

    private final String message;

    private String total;

    public BaseResponse(String code, T data, String message, String total) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.total = total;
    }

    public BaseResponse(String code, T data, String total) {
        this(code, data, "", total);
    }

    public BaseResponse(ResponseCode responseCode) {
        this(responseCode.getCode(), null, responseCode.getMessage(), "");
    }
}
