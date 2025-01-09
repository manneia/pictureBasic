package com.manneia.manneiapicturebackend.exception;

import com.alibaba.fastjson2.JSON;
import com.manneia.manneiapicturebackend.common.BaseResponse;
import com.manneia.manneiapicturebackend.common.ResultUtils;
import com.manneia.manneiapicturebackend.constant.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author luokaixuan
 * @description 全局异常处理器
 * @created 2024/12/8 17:55
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse<Object> bussinessExceptionHandler(BusinessException e) {
        log.error("BusinessException: {}", JSON.toJSONString(e));
        return ResultUtils.invokeError(ResponseCode.INTERIOR_SYSTEM_ERROR, e, "1");
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<Object> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException: {}", JSON.toJSONString(e));
        return ResultUtils.invokeError(ResponseCode.SYSTEM_ERROR, e, "1");
    }
}
