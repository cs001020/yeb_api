package com.chen.server.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试接口
 *
 * @author CHEN
 * @date 2022/09/29
 */
@RestController
@Slf4j
public class HelloController {
    @ApiOperation(value = "Hello测试接口")
    @GetMapping("/hello")
    public String hello(){
        log.info("线程测试");
        return "hello";
    }
}
