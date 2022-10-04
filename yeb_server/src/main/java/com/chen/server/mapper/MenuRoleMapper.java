package com.chen.server.mapper;

import com.chen.server.pojo.MenuRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author CHEN
 * @since 2022-09-29
 */
public interface MenuRoleMapper extends BaseMapper<MenuRole> {

    /**
     * 批量添加
     *
     * @param rid  掉
     * @param mids mids
     * @return int
     */
    int batchAdd(@Param("rid") Integer rid, @Param("mids") Integer[] mids);
}
