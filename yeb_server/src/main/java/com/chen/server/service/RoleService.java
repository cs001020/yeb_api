package com.chen.server.service;

import com.chen.server.pojo.Role;
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
public interface RoleService extends IService<Role> {
    /**
     * 根据用户id获取角色列表
     *
     * @param adminId 管理员id
     * @return {@link List}<{@link Role}>
     */
    List<Role> getRolesByAdminId(Integer adminId);
}
