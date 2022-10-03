package com.chen.server.mapper;

import com.chen.server.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author CHEN
 * @since 2022-09-29
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户id获取角色列表
     *
     * @param adminId 管理员id
     * @return {@link List}<{@link Role}>
     */
    List<Role> getRolesByAdminId(@Param("adminId") Integer adminId);

}
