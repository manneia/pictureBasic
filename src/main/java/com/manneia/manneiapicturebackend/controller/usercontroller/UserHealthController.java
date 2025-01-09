package com.manneia.manneiapicturebackend.controller.usercontroller;

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
public class UserHealthController {

    @PostMapping("/post/health")
    @ApiOperation(value = "post请求健康检查")
    public BaseResponse<String> postHealth() {
        return ResultUtils.invokeSuccess("ok", "1");
    }

    @GetMapping("/get/health")
    @ApiOperation(value = "get请求健康检查")
    public BaseResponse<String> getHealth() {
        return ResultUtils.invokeSuccess("ok", "1");
    }
}
