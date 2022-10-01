package com.chen.server.controller;


import com.chen.server.pojo.Menu;
import com.chen.server.pojo.Results;
import com.chen.server.service.AdminService;
import com.chen.server.service.MenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author CHEN
 * @since 2022-09-29
 */
@RestController
@RequestMapping("/system/cfg")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @ApiOperation(value = "通过用户id查询菜单列表")
    @GetMapping("/menu")
    public Results getMenuListByAdminId(){
        List<Menu> menuList = menuService.getMenuListByAdminId();
        return Results.success("菜单列表",menuList);
    }
}

