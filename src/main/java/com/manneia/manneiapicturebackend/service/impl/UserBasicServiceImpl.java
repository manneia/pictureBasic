package com.manneia.manneiapicturebackend.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.manneia.manneiapicturebackend.common.BaseResponse;
import com.manneia.manneiapicturebackend.common.ResultUtils;
import com.manneia.manneiapicturebackend.constant.ResponseCode;
import com.manneia.manneiapicturebackend.constant.UserConstant;
import com.manneia.manneiapicturebackend.enums.UserRoleEnum;
import com.manneia.manneiapicturebackend.exception.BusinessException;
import com.manneia.manneiapicturebackend.exception.ThrowUtils;
import com.manneia.manneiapicturebackend.mapper.UserBasicMapper;
import com.manneia.manneiapicturebackend.model.domain.User;
import com.manneia.manneiapicturebackend.model.request.user.UserAddRequest;
import com.manneia.manneiapicturebackend.model.request.user.UserLoginRequest;
import com.manneia.manneiapicturebackend.model.request.user.UserQueryRequest;
import com.manneia.manneiapicturebackend.model.request.user.UserRegisterRequest;
import com.manneia.manneiapicturebackend.model.response.user.*;
import com.manneia.manneiapicturebackend.service.UserBasicService;
import com.manneia.manneiapicturebackend.utils.EncryptDecryptUtils;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author lkx
* @description 针对表【user_basic(用户基础信息表)】的数据库操作Service实现
* @createDate 2025-01-09 13:45:01
*/
@Slf4j
@Service
public class UserBasicServiceImpl extends ServiceImpl<UserBasicMapper, User>
    implements UserBasicService{

    private static final String SALT = "manneia_salt";

    @Override
    public BaseResponse<UserRegisterVo> userRegister(String uuid, UserRegisterRequest userRegisterRequest) {
        // 1. 参数校验
        log.info("请求id:{},开始执行用户注册方法, 请求参数:{}", uuid, JSON.toJSONString(userRegisterRequest));
        ThrowUtils.throwIf(userRegisterRequest == null, ResponseCode.PARAMS_NO_EXISTS_CODE, ResponseCode.PARAMS_NO_EXISTS_CODE.getMessage());
        boolean isRequired = (
                (userRegisterRequest.getUserAccount() != null &&
                        !userRegisterRequest.getUserAccount().isEmpty())
                        && (userRegisterRequest.getUserPassword() != null &&
                        !userRegisterRequest.getUserPassword().isEmpty()) &&
                        (userRegisterRequest.getCheckPassword() != null && !userRegisterRequest.getCheckPassword().isEmpty()));
        ThrowUtils.throwIf(!isRequired, ResponseCode.PARAMS_NO_EXISTS_CODE, ResponseCode.PARAMS_NO_EXISTS_CODE.getMessage());
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        ThrowUtils.throwIf(userAccount.length() < 4 || userAccount.length() > 16, ResponseCode.PARAMS_ERROR, ResponseCode.PARAMS_ERROR.getMessage() + ": 用户账号需为4位至16位");
        ThrowUtils.throwIf(userPassword.length() < 8 || checkPassword.length() < 8, ResponseCode.PARAMS_ERROR, ResponseCode.PARAMS_ERROR.getMessage() + ": 用户密码需为8位及以上");
        ThrowUtils.throwIf(!userPassword.equals(checkPassword), ResponseCode.PARAMS_ERROR, "两次输入密码不一致");
        // 生成返回值
        UserRegisterVo userRegisterVo = new UserRegisterVo();
        // 校验用户账号是否重复
        LambdaQueryWrapper<User> queryUserMapper = new LambdaQueryWrapper<>();
        queryUserMapper.eq(User::getUserAccount, userAccount);
        try {
            Long count = this.baseMapper.selectCount(queryUserMapper);
            ThrowUtils.throwIf(count > 0, ResponseCode.USER_EXIST_ERROR, ResponseCode.USER_EXIST_ERROR.getMessage() + ",请换一个账号名吧！");
        } catch (MyBatisSystemException e) {
            log.error("请求id:{}校验用户账号是否重复,数据库异常:{}", uuid, JSON.toJSONString(e.getLocalizedMessage()));
            throw new BusinessException(ResponseCode.SYSTEM_ERROR, ResponseCode.SYSTEM_ERROR.getMessage() + ":校验账号失败,数据库错误");
        }
        // 加密用户信息
        userPassword = EncryptDecryptUtils.getEncryptPassword(userPassword, SALT);
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(userPassword);
        user.setUserName("default");
        user.setUserRole(UserRoleEnum.USER.getValue());
        // 保存用户信息
        try {
            boolean saveResult = this.save(user);
            // 若用户注册失败，则抛出异常
            ThrowUtils.throwIf(!saveResult, ResponseCode.SYSTEM_ERROR, ResponseCode.SYSTEM_ERROR.getMessage() + ":注册失败,数据库错误");
            // 设置返回对象
            userRegisterVo.setId(user.getId());
            userRegisterVo.setInvokeResult(Boolean.TRUE);
            userRegisterVo.setInvokeMessage("注册成功");
        } catch (MyBatisSystemException e) {
            log.error("请求id:{}用户注册,数据库异常:{}", uuid, JSON.toJSONString(e.getLocalizedMessage()));
            throw new BusinessException(ResponseCode.SYSTEM_ERROR, ResponseCode.SYSTEM_ERROR.getMessage() + ":注册失败,数据库错误");
        }
        log.info("请求id:{}用户注册,返回结果:{}", uuid, JSON.toJSONString(userRegisterVo));
        return ResultUtils.invokeSuccess(userRegisterVo, 1);
    }

    @Override
    public BaseResponse<UserLoginVo> userLogin(String uuid, UserLoginRequest userLoginRequest, HttpServletRequest request) {
        // 参数校验
        log.info("请求id:{},开始执行用户登录方法, 请求参数:{}", uuid, JSON.toJSONString(userLoginRequest));
        ThrowUtils.throwIf(userLoginRequest == null, ResponseCode.PARAMS_NO_EXISTS_CODE, ResponseCode.PARAMS_NO_EXISTS_CODE.getMessage());
        boolean isRequired = (
                (userLoginRequest.getUserAccount() != null &&
                        !userLoginRequest.getUserAccount().isEmpty())
                        && (userLoginRequest.getUserPassword() != null &&
                        !userLoginRequest.getUserPassword().isEmpty()));
        ThrowUtils.throwIf(!isRequired, ResponseCode.PARAMS_NO_EXISTS_CODE, ResponseCode.PARAMS_NO_EXISTS_CODE.getMessage());
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        ThrowUtils.throwIf(userAccount.length() < 4 || userAccount.length() > 16, ResponseCode.PARAMS_ERROR, ResponseCode.PARAMS_ERROR.getMessage() + ": 用户账号需为4位至16位");
        ThrowUtils.throwIf(userPassword.length() < 8, ResponseCode.PARAMS_ERROR, ResponseCode.PARAMS_ERROR.getMessage() + ": 用户密码需为8位及以上");
        userPassword = EncryptDecryptUtils.getEncryptPassword(userPassword, SALT);
        // 校验用户账号是否存在
        LambdaQueryWrapper<User> queryUserMapper = new LambdaQueryWrapper<>();
        queryUserMapper.eq(User::getUserAccount, userAccount)
                .eq(User::getUserPassword, userPassword);
        User user;
        try {
            user = this.baseMapper.selectOne(queryUserMapper);
            if (user == null) {
                log.error("请求id:{}用户登录,用户账号不存在:{}", uuid, JSON.toJSONString(userAccount));
                throw new BusinessException(ResponseCode.USER_EXIST_ERROR, "账号不存在,请换一个账号名吧！");
            }
        } catch (MyBatisSystemException e) {
            log.error("请求id:{}用户登录,数据库异常:{}", uuid, JSON.toJSONString(e.getLocalizedMessage()));
            throw new BusinessException(ResponseCode.SYSTEM_ERROR, ResponseCode.SYSTEM_ERROR.getMessage() + ":登录失败,数据库错误");
        }
        // 记录用户的登录态
        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, user);
        UserLoginVo userLoginVo = this.getLoginUserVo(user).getData();
        userLoginVo.setInvokeMessage("登录成功");
        log.info("请求id:{}用户登录,返回结果:{}", uuid, JSON.toJSONString(userLoginVo));
        return ResultUtils.invokeSuccess(userLoginVo, 1);
    }

    @Override
    public BaseResponse<UserLoginVo> getLoginUserVo(User user) {
        log.info("开始执行获取登录用户信息脱敏方法, 用户信息:{}", JSON.toJSONString(user));
        if (user == null) {
            return ResultUtils.invokeSuccess(null, 1);
        }
        UserLoginVo loginUserVoVo = new UserLoginVo();
        BeanUtils.copyProperties(user, loginUserVoVo);
        loginUserVoVo.setInvokeResult(Boolean.TRUE);
        loginUserVoVo.setInvokeMessage("获取当前登录用户信息成功");
        log.info("执行获取登录用信息脱敏方法完成,返回结果:{}", JSON.toJSONString(loginUserVoVo));
        return ResultUtils.invokeSuccess(loginUserVoVo, 1);
    }

    @Override
    public BaseResponse<UserVo> getUserVo(User user) {
        log.info("开始执行获取用户信息脱敏方法, 用户信息:{}", JSON.toJSONString(user));
        UserVo userVo = this.handlerUser(user);
        if (userVo == null) {
            return ResultUtils.invokeSuccess(null, 1);
        }
        log.info("执行用户信息脱敏方法完成,返回结果:{}", JSON.toJSONString(userVo));
        return ResultUtils.invokeSuccess(userVo, 1);
    }

    @Override
    public BaseResponse<List<UserVo>> getUserVoList(List<User> userList) {
        log.info("开始执行批量获取用户信息脱敏方法, 用户信息:{}", JSON.toJSONString(userList));
        ThrowUtils.throwIf(userList == null, ResponseCode.PARAMS_ERROR, "用户列表为必传");
        if (CollUtil.isEmpty(userList)) {
            return ResultUtils.invokeSuccess(new ArrayList<>(), 1);
        }
        List<UserVo> userVoList = userList.stream().map(this::handlerUser).collect(Collectors.toList());
        log.info("执行批量获取用户信息脱敏方法完成,返回结果:{}", JSON.toJSONString(userVoList));
        return ResultUtils.invokeSuccess(userVoList, userVoList.size());
    }


    @Override
    public BaseResponse<User> getCurrentLoginUserVo(String uuid, HttpServletRequest request) {
        log.info("请求id:{}开始执行获取当前登录用户信息方法", uuid);
        // 是否登录
        Object userObject = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User user = (User) userObject;
        if (user == null || user.getId() == null) {
            throw new BusinessException(ResponseCode.NOT_LOGIN_ERROR);
        }
        Long userBasicId = user.getId();
        try {
            User currentUser = this.getById(userBasicId);
            if (currentUser == null) {
                log.error("请求id:{}获取当前登录用户信息,用户不存在:{}", uuid, JSON.toJSONString(userBasicId));
                throw new BusinessException(ResponseCode.NOT_LOGIN_ERROR);
            }
            log.info("请求id:{}获取当前登录用户信息,用户信息:{}", uuid, JSON.toJSONString(currentUser));
            return ResultUtils.invokeSuccess(currentUser, 1);
        } catch (MyBatisSystemException e) {
            log.error("请求id:{}获取当前登录用户信息,数据库异常:{}", uuid, JSON.toJSONString(e.getLocalizedMessage()));
            throw new BusinessException(ResponseCode.SYSTEM_ERROR, ResponseCode.SYSTEM_ERROR.getMessage() + ":获取用户信息失败,数据库错误");
        }
    }

    @Override
    public BaseResponse<UserLogoutVo> userLogout(String uuid, HttpServletRequest request) {
        log.info("请求id:{}开始执行用户退出方法", uuid);
        // 生成返回对象
        UserLogoutVo userLogoutVo = new UserLogoutVo();
        // 是否登录
        Object userObj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if (userObj == null) {
            throw new BusinessException(ResponseCode.NOT_LOGIN_ERROR);
        }
        // 移除登录态
        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
        userLogoutVo.setLogOutIsSuccess(Boolean.TRUE);
        userLogoutVo.setInvokeResult(Boolean.TRUE);
        userLogoutVo.setInvokeMessage("退出成功");
        log.info("请求id:{}用户退出,返回结果:{}", uuid, JSON.toJSONString(userLogoutVo));
        return ResultUtils.invokeSuccess(userLogoutVo, 1);
    }

    @Override
    public LambdaQueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (userQueryRequest == null) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = userQueryRequest.getId();
        String userName = userQueryRequest.getUserName();
        String userAccount = userQueryRequest.getUserAccount();
        String userProfile = userQueryRequest.getUserProfile();
        String userRole = userQueryRequest.getUserRole();
        String sortField = userQueryRequest.getSortField();
        String sortOrder = userQueryRequest.getSortOrder();
        queryWrapper.eq(ObjectUtil.isNotNull(id), User::getId, id)
                .eq(CharSequenceUtil.isNotBlank(userRole), User::getUserRole, userRole)
                .like(CharSequenceUtil.isNotBlank(userName), User::getUserName, userName)
                .like(CharSequenceUtil.isNotBlank(userAccount), User::getUserAccount, userAccount)
                .like(CharSequenceUtil.isNotBlank(userProfile), User::getUserProfile, userProfile);
        if (CharSequenceUtil.isNotBlank(sortField) && CharSequenceUtil.isNotBlank(sortOrder)) {
            queryWrapper.last("order by " + sortField + ("ascend".equals(sortOrder) ? " ASC" : " DESC"));
        }
        return queryWrapper;
    }

    @Override
    public BaseResponse<UserAddVo> userAdd(String uuid, UserAddRequest userAddRequest) {
        // 1. 参数校验
        log.info("请求id:{},开始执行创建用户方法, 请求参数:{}", uuid, JSON.toJSONString(userAddRequest));
        ThrowUtils.throwIf(userAddRequest == null, ResponseCode.PARAMS_NO_EXISTS_CODE, ResponseCode.PARAMS_NO_EXISTS_CODE.getMessage());
        UserAddVo userAddVo = new UserAddVo();
        User user = new User();
        BeanUtils.copyProperties(userAddRequest, user);
        // 默认密码
        final String defaultPassword = "12345678";
        String encryptPassword = EncryptDecryptUtils.getEncryptPassword(defaultPassword, SALT);
        user.setUserPassword(encryptPassword);
        try {
            boolean save = this.save(user);
            ThrowUtils.throwIf(!save, ResponseCode.OPERATION_ERROR, ResponseCode.OPERATION_ERROR.getMessage() + ":创建用户失败");
            userAddVo.setId(user.getId());
            userAddVo.setInvokeResult(Boolean.TRUE);
            userAddVo.setInvokeMessage("创建用户成功");
        } catch (Exception e) {
            log.error("请求id:{},创建用户失败, 请求参数:{}", uuid, JSON.toJSONString(userAddRequest));
            throw new BusinessException(ResponseCode.OPERATION_ERROR, ResponseCode.OPERATION_ERROR.getMessage() + ":创建用户失败");
        }
        return ResultUtils.invokeSuccess(userAddVo, 1);
    }

    @Override
    public BaseResponse<User> getUserById(String uuid, Long id) {
        ThrowUtils.throwIf(id < 0, ResponseCode.PARAMS_ERROR, "id错误");
        log.info("请求id:{},开始执行根据用户id获取用户信息方法, 请求参数:{}", uuid, JSON.toJSONString(id));
        User user = this.getById(id);
        log.info("请求id:{},根据用户id获取用户信息成功, 用户信息:{}", uuid, JSON.toJSONString(user));
        ThrowUtils.throwIf(user == null, ResponseCode.PARAMS_ERROR, "用户不存在");
        return ResultUtils.invokeSuccess(user, 1);
    }

    private UserVo handlerUser(User user) {
        if (user == null) {
            return null;
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return userVo;
    }
}
