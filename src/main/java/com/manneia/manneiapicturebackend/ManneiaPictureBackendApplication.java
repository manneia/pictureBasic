package com.manneia.manneiapicturebackend;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author lkx
 */
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@MapperScan("com.manneia.manneiapicturebackend.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
public class ManneiaPictureBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManneiaPictureBackendApplication.class, args);
    }

}
