package com.chen.server.service;

import com.chen.server.pojo.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author CHEN
 * @since 2022-09-29
 */
public interface MenuService extends IService<Menu> {

    /**
     * 根据用户id菜单列表
     *
     * @return {@link List}<{@link Menu}>
     */
    List<Menu> getMenuListByAdminId();
}
