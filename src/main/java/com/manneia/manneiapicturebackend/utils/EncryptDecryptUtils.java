package com.manneia.manneiapicturebackend.utils;

import com.manneia.manneiapicturebackend.constant.ResponseCode;
import com.manneia.manneiapicturebackend.exception.BusinessException;
import org.springframework.util.DigestUtils;

/**
 * @author luokaixuan
 * @description com.manneia.manneiapicturebackend.utils
 * @created 2025/1/9 15:02
 */
public class EncryptDecryptUtils {

    private EncryptDecryptUtils() {
        throw new BusinessException(ResponseCode.INSTANTIATION_ERROR.getCode(),ResponseCode.INSTANTIATION_ERROR.getMessage());
    }

    /**
     * 加密
     *
     * @param userPassword 待加密字符串
     * @param salt         盐值
     * @return 加密后的字符串
     */
    public static String getEncryptPassword(String userPassword, String salt) {
        return DigestUtils.md5DigestAsHex((userPassword + salt).getBytes());
    }
}
