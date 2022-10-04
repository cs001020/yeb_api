package com.chen.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chen.server.pojo.MenuRole;
import com.chen.server.mapper.MenuRoleMapper;
import com.chen.server.service.MenuRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author CHEN
 * @since 2022-09-29
 */
@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements MenuRoleService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRoleMenu(Integer rid, Integer[] mids) {
        baseMapper.delete(new LambdaQueryWrapper<MenuRole>().eq(MenuRole::getRid,rid));
        if (null==mids||0==mids.length){
            return true;
        }
        return baseMapper.batchAdd(rid, mids) > 0;
    }
}
