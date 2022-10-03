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
    @ApiOperation(value = "Hello测试接口1")
    @GetMapping("/hello")
    public String hello1(){
        log.info("线程测试");
        return "hello";
    }

    @ApiOperation(value = "Hello测试接口2")
    @GetMapping("/employee/basic/**")
    public String hello2(){
        return "/employee/basic/**";
    }
    @ApiOperation(value = "Hello测试接口3")
    @GetMapping("/employee/advanced/**")
    public String hello3(){
        return "/employee/advanced/**";
    }
}
