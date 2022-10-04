package com.chen.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chen.server.pojo.MenuRole;
import com.chen.server.pojo.Results;
import com.chen.server.pojo.Role;
import com.chen.server.service.MenuRoleService;
import com.chen.server.service.MenuService;
import com.chen.server.service.RoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限控制器
 *
 * @author CHEN
 * @date 2022/10/04
 */
@RestController
@RequestMapping("/system/basic/permission")
public class PermissionController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private MenuRoleService menuRoleService;

    @ApiOperation(value = "获取所有角色")
    @GetMapping("/role")
    public Results getAllRole(){
        return Results.success("success",roleService.list());
    }
    @ApiOperation(value = "添加角色")
    @PostMapping("/role")
    public Results addRole(@RequestBody Role role){
        if (!role.getName().startsWith("ROLE_")){
            role.setName("ROLE_"+role.getName());
        }
        if (roleService.save(role)){
            return Results.success("添加成功");
        }
        return Results.error("添加失败");
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/role/{rid}")
    public Results deleteRole(@PathVariable("rid") Integer rid){
        if (roleService.removeById(rid)){
            return Results.success("删除成功");
        }
        return Results.error("删除失败");
    }

    @ApiOperation(value = "查询所有菜单")
    @GetMapping("/menu")
    public Results getAllMenus(){
        return Results.success("success",menuService.getAllMenus());
    }

    @ApiOperation(value = "根据角色id查询所有菜单id")
    @GetMapping("/menu/{rid}")
    public Results getAllMenusByRoleId(@PathVariable("rid") Integer rid){
        List<MenuRole> menuRoleList = menuRoleService.list(new LambdaQueryWrapper<MenuRole>().eq(MenuRole::getRid, rid));
        List<Integer> midList = menuRoleList.stream().map(MenuRole::getMid).collect(Collectors.toList());
        return Results.success("success",midList);
    }

    @ApiOperation(value = "更新角色菜单")
    @PostMapping("/menu")
    public Results updateRoleMenu(Integer rid,Integer[] mids){
        if (menuRoleService.updateRoleMenu(rid, mids)){
            return Results.success("修改成功");
        }
        return Results.error("修改失败");
    }
}
