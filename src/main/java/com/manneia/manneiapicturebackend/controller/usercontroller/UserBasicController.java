package com.manneia.manneiapicturebackend.controller.usercontroller;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.manneia.manneiapicturebackend.annotation.AuthCheck;
import com.manneia.manneiapicturebackend.common.BaseResponse;
import com.manneia.manneiapicturebackend.common.DeleteRequest;
import com.manneia.manneiapicturebackend.common.ResultUtils;
import com.manneia.manneiapicturebackend.constant.ResponseCode;
import com.manneia.manneiapicturebackend.constant.UserConstant;
import com.manneia.manneiapicturebackend.exception.BusinessException;
import com.manneia.manneiapicturebackend.exception.ThrowUtils;
import com.manneia.manneiapicturebackend.model.domain.User;
import com.manneia.manneiapicturebackend.model.request.user.*;
import com.manneia.manneiapicturebackend.model.response.user.*;
import com.manneia.manneiapicturebackend.service.UserBasicService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * @author luokaixuan
 * @description 用户相关接口
 * @created 2025/1/9 16:21
 */
@Slf4j
@RestController
@RequestMapping("/user")
@ApiSupport(author = "luokaixuan")
public class UserBasicController {

    @Resource
    private UserBasicService userBasicService;

    @PostMapping("register")
    @ApiOperation(value = "用户注册")
    @ApiOperationSupport(author = "luokaixuan")
    public BaseResponse<UserRegisterVo> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        return userBasicService.userRegister(UUID.randomUUID().toString(), userRegisterRequest);
    }

    @PostMapping("login")
    @ApiOperation(value = "用户登录")
    @ApiOperationSupport(author = "luokaixuan")
    public BaseResponse<UserLoginVo> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        return userBasicService.userLogin(UUID.randomUUID().toString(), userLoginRequest, request);
    }

    @GetMapping("/get/login")
    @ApiOperation(value = "获取登录用户信息")
    @ApiOperationSupport(author = "luokaixuan")
    public BaseResponse<UserLoginVo> getLoginUser(HttpServletRequest request) {
        return userBasicService.getLoginUserVo(userBasicService.getCurrentLoginUserVo(UUID.randomUUID().toString(), request).getData());
    }

    @PostMapping("/logout")
    @ApiOperation(value = "用户注销")
    @ApiOperationSupport(author = "luokaixuan")
    public BaseResponse<UserLogoutVo> userLogout(HttpServletRequest request) {
        return userBasicService.userLogout(UUID.randomUUID().toString(), request);
    }

    //region
    @PostMapping("add")
    @ApiOperation(value = "创建用户")
    @ApiOperationSupport(author = "luokaixuan")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<UserAddVo> addUser(@RequestBody UserAddRequest userAddRequest) {
        return userBasicService.userAdd(UUID.randomUUID().toString(), userAddRequest);
    }

    @GetMapping("get")
    @ApiOperation(value = "根据id获取用户")
    @ApiOperationSupport(author = "luokaixuan")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<User> getUserById(@RequestParam("id") Long id) {
        return userBasicService.getUserById(UUID.randomUUID().toString(), id);
    }

    @GetMapping("/get/vo")
    @ApiOperation(value = "根据id获取包装类")
    @ApiOperationSupport(author = "luokaixuan")
    public BaseResponse<UserVo> getUserVoById(@RequestParam("id") long id) {
        return userBasicService.getUserVo(getUserById(id).getData());
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除用户")
    @ApiOperationSupport(author = "luokaixuan")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteUser(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR);
        }
        try {
            boolean removeResult = userBasicService.removeById(deleteRequest.getId());
            ThrowUtils.throwIf(!removeResult, ResponseCode.OPERATION_ERROR, ResponseCode.OPERATION_ERROR.getMessage() + ":删除用户失败");
        } catch (Exception e) {
            log.error("请求id:{},删除用户失败, 请求参数:{}", deleteRequest.getId(), JSON.toJSONString(deleteRequest));
            throw new BusinessException(ResponseCode.OPERATION_ERROR, ResponseCode.OPERATION_ERROR.getMessage() + ":删除用户失败");
        }
        return ResultUtils.invokeSuccess(true, 1);
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新用户")
    @ApiOperationSupport(author = "luokaixuan")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        if (userUpdateRequest == null || userUpdateRequest.getId() == null) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR);
        }
        User user = new User();
        BeanUtils.copyProperties(userUpdateRequest, user);
        boolean result = userBasicService.updateById(user);
        ThrowUtils.throwIf(!result, ResponseCode.OPERATION_ERROR);
        return ResultUtils.invokeSuccess(true, 1);
    }

    @PostMapping("/list/page/vo")
    @ApiOperation(value = "分页获取用户封装列表（仅管理员）")
    @ApiOperationSupport(author = "luokaixuan")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<UserVo>> listUserVoByPage(@RequestBody UserQueryRequest userQueryRequest) {
        ThrowUtils.throwIf(userQueryRequest == null, ResponseCode.PARAMS_ERROR);
        int currentPage = userQueryRequest.getCurrentPage();
        int pageSize = userQueryRequest.getPageSize();
        Page<User> userPage = userBasicService.page(new Page<>(currentPage, pageSize),
                userBasicService.getQueryWrapper(userQueryRequest));
        Page<UserVo> userVoPage = new Page<>(currentPage, pageSize, userPage.getTotal());
        List<UserVo> userVoList = userBasicService.getUserVoList(userPage.getRecords()).getData();
        userVoPage.setRecords(userVoList);
        return ResultUtils.invokeSuccess(userVoPage, (int) userVoPage.getTotal());
    }
    //endregion
}
