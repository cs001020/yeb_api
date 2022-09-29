package com.chen.server.service;

import com.chen.server.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.server.pojo.Menu;
import com.chen.server.pojo.Results;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author CHEN
 * @since 2022-09-29
 */
public interface AdminService extends IService<Admin> {

    /**
     * 登录之后返回token
     *
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @param request  请求
     * @return {@link Results}
     */
    Results login(String username, String password, String code, HttpServletRequest request);

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return {@link Admin}
     */
    Results getAdminByUserName(String username);

}
