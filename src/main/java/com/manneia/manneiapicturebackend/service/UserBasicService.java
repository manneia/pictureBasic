package com.manneia.manneiapicturebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.manneia.manneiapicturebackend.common.BaseResponse;
import com.manneia.manneiapicturebackend.model.domain.User;
import com.manneia.manneiapicturebackend.model.request.user.UserAddRequest;
import com.manneia.manneiapicturebackend.model.request.user.UserLoginRequest;
import com.manneia.manneiapicturebackend.model.request.user.UserQueryRequest;
import com.manneia.manneiapicturebackend.model.request.user.UserRegisterRequest;
import com.manneia.manneiapicturebackend.model.response.user.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author lkx
* @description 针对表【user_basic(用户基础信息表)】的数据库操作Service
* @createDate 2025-01-09 13:45:01
*/
public interface UserBasicService extends IService<User> {

    /**
     * 用户注册
     *
     * @param uuid                请求唯一标识
     * @param userRegisterRequest 用户注册请求参数
     * @return 新用户 id
     */
    BaseResponse<UserRegisterVo> userRegister(String uuid, UserRegisterRequest userRegisterRequest);

    /**
     * 用户登录
     *
     * @param uuid             请求唯一标识
     * @param userLoginRequest 用户登录请求参数
     * @param request          请求
     * @return 用户信息
     */
    BaseResponse<UserLoginVo> userLogin(String uuid, UserLoginRequest userLoginRequest, HttpServletRequest request);

    /**
     * 获取登录脱敏用户信息
     *
     * @param user 用户信息
     * @return 脱敏用户信息
     */
    BaseResponse<UserLoginVo> getLoginUserVo(User user);

    /**
     * 获取脱敏用户信息
     *
     * @param user 用户对象
     * @return 脱敏用户信息
     */
    BaseResponse<UserVo> getUserVo(User user);

    /**
     * 获取脱敏用户信息列表
     *
     * @param userList 用户对象列表
     * @return 脱敏用户信息列表
     */
    BaseResponse<List<UserVo>> getUserVoList(List<User> userList);

    /**
     * 获取当前登录的用户信息
     *
     * @param uuid    请求唯一标识
     * @param request 请求
     * @return 用户信息
     */
    BaseResponse<User> getCurrentLoginUserVo(String uuid, HttpServletRequest request);

    /**
     * 用户注销
     *
     * @param uuid    请求唯一标识
     * @param request 请求
     * @return 注销结果
     */
    BaseResponse<UserLogoutVo> userLogout(String uuid, HttpServletRequest request);

    /**
     * 根据查询条件对象生成queryWrapper
     *
     * @param userQueryRequest 查询条件对象
     * @return queryWrapper
     */
    LambdaQueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);

    //region 管理员相关接口
    /**
     * 创建用户
     * @param uuid 请求id
     * @param userAddRequest 用户创建请求体
     * @return 用户创建返回对象
     */
    BaseResponse<UserAddVo> userAdd(String uuid, UserAddRequest userAddRequest);

    /**
     * 根据id获取用户对象
     *
     * @param uuid 请求id
     * @param id 用户id
     * @return 用户对象
     */
    BaseResponse<User> getUserById(String uuid, Long id);
    //endregion
}
