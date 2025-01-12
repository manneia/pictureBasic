package com.manneia.manneiapicturebackend.annotation;

import com.manneia.manneiapicturebackend.constant.UserConstant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author luokaixuan
 * @description com.manneia.manneiapicturebackend.annotation
 * @created 2025/1/11 14:48
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {

    String mustRole() default UserConstant.DEFAULT_ROLE;
}
