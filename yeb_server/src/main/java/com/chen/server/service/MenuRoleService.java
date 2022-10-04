package com.chen.server.service;

import com.chen.server.pojo.MenuRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author CHEN
 * @since 2022-09-29
 */
public interface MenuRoleService extends IService<MenuRole> {

    /**
     * 更新角色菜单
     *
     * @param rid  掉
     * @param mids mids
     * @return boolean
     */
    boolean updateRoleMenu(Integer rid, Integer[] mids);
}
