package com.chen.server.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.chen.server.pojo.Admin;
import com.chen.server.pojo.Menu;
import com.chen.server.mapper.MenuMapper;
import com.chen.server.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author CHEN
 * @since 2022-09-29
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Autowired
    private RedisTemplate  redisTemplate;

    @Override
    public List<Menu> getMenuListByAdminId() {
        Integer adminId = ((Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        //从redis获取数据
        List<Menu> menus = (List<Menu>) valueOperations.get("menus:" + adminId);
        if (CollectionUtils.isEmpty(menus)){
            menus = baseMapper.getMenusByAdminId(adminId);
            valueOperations.set("menus:" + adminId,menus);
        }
        return menus;
    }

}
