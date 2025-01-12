package com.manneia.manneiapicturebackend.controller.usercontroller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.manneia.manneiapicturebackend.common.BaseResponse;
import com.manneia.manneiapicturebackend.common.ResultUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luokaixuan
 * @description com.manneia.manneiapicturebackend.controller.userController
 * @created 2024/12/8 18:42
 */
@RestController
@RequestMapping("/user")
@ApiSupport(author = "luokaixuan")
public class UserHealthController {

    @PostMapping("/post/health")
    @ApiOperation(value = "post请求健康检查")
    @ApiOperationSupport(author = "张宇")
    public BaseResponse<String> postHealth() {
        return ResultUtils.invokeSuccess("ok", 1);
    }

    @GetMapping("/get/health")
    @ApiOperation(value = "get请求健康检查")
    public BaseResponse<String> getHealth() {
        return ResultUtils.invokeSuccess("ok", 1);
    }

}
