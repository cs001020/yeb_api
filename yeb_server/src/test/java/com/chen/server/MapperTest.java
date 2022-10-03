package com.chen.server;

import com.alibaba.fastjson.JSON;
import com.chen.server.mapper.MenuMapper;
import com.chen.server.mapper.RoleMapper;
import com.chen.server.pojo.Menu;
import com.chen.server.pojo.Role;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class MapperTest {
    @Resource
    MenuMapper menuMapper;
    @Resource
    RoleMapper roleMapper;
    @Test
    public void test1(){
        List<Menu> menuListWithRoles = menuMapper.getMenuListWithRoles();
        System.out.println(JSON.toJSONString(menuListWithRoles));
    }
    @Test
    public void test2(){
        List<Role> rolesByAdminId = roleMapper.getRolesByAdminId(2);
        System.out.println(JSON.toJSONString(rolesByAdminId));
    }

}
