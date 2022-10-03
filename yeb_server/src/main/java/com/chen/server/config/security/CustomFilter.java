package com.chen.server.config.security;

import com.chen.server.pojo.Menu;
import com.chen.server.pojo.Role;
import com.chen.server.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * 根据请求url获取所需要的角色
 *
 * @author CHEN
 * @date 2022/10/03
 */
@Component
public class CustomFilter implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private MenuService menuService;
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        //获取请求url
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        List<Menu> menuListWithRoles = menuService.getMenuListWithRoles();
        for (Menu menu : menuListWithRoles) {
            //判断请求url与菜单时候匹配
            if (menu.getUrl().equals(requestUrl)){
                String[] strings = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
                return SecurityConfig.createList(strings);
            }
        }
        //登陆即可访问
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
