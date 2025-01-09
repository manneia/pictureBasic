package com.manneia.manneiapicturebackend.exception;

import com.manneia.manneiapicturebackend.constant.ResponseCode;
import lombok.Getter;

/**
 * @author luokaixuan
 * @description 自定义异常
 * @created 2024/12/8 17:21
 */
@Getter
@SuppressWarnings("unused")
public class BusinessException extends RuntimeException {

    /**
     * 错误码
     */
    private final String code;

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.code = responseCode.getCode();
    }

    public BusinessException(ResponseCode responseCode, String message) {
        super(message);
        this.code = responseCode.getCode();
    }
}
