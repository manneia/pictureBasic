package com.manneia.manneiapicturebackend.aop;

import com.manneia.manneiapicturebackend.annotation.AuthCheck;
import com.manneia.manneiapicturebackend.common.BaseResponse;
import com.manneia.manneiapicturebackend.constant.ResponseCode;
import com.manneia.manneiapicturebackend.exception.BusinessException;
import com.manneia.manneiapicturebackend.model.domain.User;
import com.manneia.manneiapicturebackend.enums.UserRoleEnum;
import com.manneia.manneiapicturebackend.service.UserBasicService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author luokaixuan
 * @description com.manneia.manneiapicturebackend.aop
 * @created 2025/1/11 16:10
 */
@Aspect
@Component
public class AuthInterceptor {

    @Resource
    private UserBasicService userBasicService;

    /**
     * 切面拦截器
     * @param joinPoint 切点
     * @param authCheck 权限校验
     * @return 返回校验结果，并放行
     * @throws Throwable 异常
     */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        String mustRole = authCheck.mustRole();
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        // 当前登录用户
        BaseResponse<User> currentLoginUserVo = userBasicService.getCurrentLoginUserVo(UUID.randomUUID().toString(), request);
        UserRoleEnum mustRoleEnum = UserRoleEnum.getEnumByValue(mustRole);
        // 如果不需要权限，则直接放行
        if (mustRoleEnum == null) {
            return joinPoint.proceed();
        }
        // 获取当前用户的权限
        UserRoleEnum userRoleEnum = UserRoleEnum.getEnumByValue(currentLoginUserVo.getData().getUserRole());
        if (userRoleEnum == null) {
            throw new BusinessException(ResponseCode.NO_AUTH_ERROR);
        }
        // 必须拥有管理员权限，但是用户没有，则拒绝
        if (UserRoleEnum.ADMIN.equals(mustRoleEnum) && !UserRoleEnum.ADMIN.equals(userRoleEnum)) {
            throw new BusinessException(ResponseCode.NO_AUTH_ERROR);
        }
        return joinPoint.proceed();
    }
}
