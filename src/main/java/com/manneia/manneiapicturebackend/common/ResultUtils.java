package com.manneia.manneiapicturebackend.common;

import com.manneia.manneiapicturebackend.constant.ResponseCode;

/**
 * @author luokaixuan
 * @description 接口返回结果工具类
 * @created 2024/12/8 17:42
 */
@SuppressWarnings("unused")
public class ResultUtils {
    private ResultUtils() {
        throw new UnsupportedOperationException(ResponseCode.INSTANTIATION_ERROR.getMessage());
    }

    public static <T> BaseResponse<T> invokeSuccess(T data, int size) {
        return new BaseResponse<>(ResponseCode.INVOKE_SUCCESS_CODE.getCode(), data, ResponseCode.INVOKE_SUCCESS_CODE.getMessage(), size);
    }

    public static <T> BaseResponse<T> invokeError(ResponseCode responseCode, T data, int size) {
        return new BaseResponse<>(responseCode.getCode(), data, ResponseCode.INVOKE_ERROR_CODE.getMessage(), size);
    }
}
