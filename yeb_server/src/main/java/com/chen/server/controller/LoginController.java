package com.chen.server.controller;

import com.chen.server.pojo.Admin;
import com.chen.server.pojo.AdminLoginParam;
import com.chen.server.pojo.Results;
import com.chen.server.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * 登录控制器
 *
 * @author CHEN
 * @date 2022/09/29
 */
@RestController
@Api(tags = "LoginController")
public class LoginController {
    @Autowired
    private AdminService adminService;

    @ApiOperation(value = "登陆成功之后返回token")
    @PostMapping("/login")
    public Results login(@RequestBody AdminLoginParam adminLoginParam, HttpServletRequest request) {
        return adminService.login(adminLoginParam.getUsername(),
                adminLoginParam.getPassword(),
                adminLoginParam.getCode(),
                request);
    }

    @ApiOperation(value = "获取当前登陆用户的信息")
    @GetMapping("/admin")
    public Results getAdminInfo(Principal principal) {
        if (null == principal) {
            return Results.error("非法请求");
        }
        String username = principal.getName();
        return adminService.getAdminByUserName(username);
    }

    @ApiOperation(value = "退出登陆")
    @PostMapping("/logoff")
    public Results logout(HttpServletRequest request) {
        return Results.success("注销成功");
    }
}
