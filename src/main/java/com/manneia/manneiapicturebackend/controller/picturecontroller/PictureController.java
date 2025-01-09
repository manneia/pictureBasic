package com.manneia.manneiapicturebackend.controller.picturecontroller;

import com.manneia.manneiapicturebackend.common.BaseResponse;
import com.manneia.manneiapicturebackend.common.ResultUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luokaixuan
 * @description com.manneia.manneiapicturebackend.controller.picturecontroller
 * @created 2024/12/22 17:12
 */
@RestController
@RequestMapping("/picture")
public class PictureController {

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
