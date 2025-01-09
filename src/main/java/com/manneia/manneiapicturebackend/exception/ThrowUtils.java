package com.manneia.manneiapicturebackend.exception;

import com.manneia.manneiapicturebackend.constant.ResponseCode;

/**
 * @author luokaixuan
 * @description 异常处理工具类
 * @created 2024/12/8 17:26
 */
@SuppressWarnings("unused")
public class ThrowUtils {

    private ThrowUtils() {
        throw new UnsupportedOperationException(ResponseCode.INSTANTIATION_ERROR.getMessage());
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition 判断条件
     * @param e         异常
     */
    public static void throwIf(boolean condition, RuntimeException e) {
        if (condition) {
            throw e;
        }
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition 判断条件
     * @param responseCode 错误码
     */
    public static void throwIf(boolean condition, ResponseCode responseCode) {
        throwIf(condition, new BusinessException(responseCode));
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition 判断条件
     * @param responseCode 错误码
     * @param message   错误信息
     */
    public static void throwIf(boolean condition, ResponseCode responseCode, String message) {
        throwIf(condition, new BusinessException(responseCode, message));
    }
}
