package com.chen.server.mapper;

import com.chen.server.pojo.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.server.pojo.Role;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author CHEN
 * @since 2022-09-29
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 通过用户id查询菜单列表
     *
     * @param id id
     * @return {@link List}<{@link Menu}>
     */
    List<Menu> getMenusByAdminId(Integer id);


    /**
     * 获取菜单和角色列表
     *
     * @return {@link List}<{@link Menu}>
     */
    List<Menu> getMenuListWithRoles();
}
