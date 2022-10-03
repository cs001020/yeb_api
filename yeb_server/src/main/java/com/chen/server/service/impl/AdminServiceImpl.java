package com.chen.server.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chen.server.config.security.JwtTokenUtil;
import com.chen.server.pojo.Admin;
import com.chen.server.mapper.AdminMapper;
import com.chen.server.pojo.Results;
import com.chen.server.pojo.Role;
import com.chen.server.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.server.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author CHEN
 * @since 2022-09-29
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RoleService roleService;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * 登录之后返回Token
     *
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @param request  请求
     * @return {@link Results}
     */
    @Override
    public Results login(String username, String password, String code, HttpServletRequest request) {
        String captcha = (String) request.getSession().getAttribute("captcha");
        if (StringUtils.isEmpty(captcha)||StringUtils.isEmpty(code)||!captcha.equalsIgnoreCase(code)){
            return Results.error("验证码错误");
        }
        //登陆
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (null == userDetails || !passwordEncoder.matches(password, userDetails.getPassword())) {
            return Results.error("用户名或者密码不正确");
        }
        if (!userDetails.isEnabled()) {
            return Results.error("账号被禁用，请联系管理员");
        }
        //更新登陆用户对象
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>(2);
        tokenMap.put("token", token);
        tokenMap.put("tokenHeader", tokenHeader);
        tokenMap.put("tokenHead", tokenHead);
        return Results.success("登陆成功", tokenMap);
    }

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return {@link Admin}
     */
    @Override
    public Admin getAdminByUserName(String username) {
        return adminMapper.selectOne(new LambdaQueryWrapper<Admin>()
                .eq(Admin::getUsername, username).
                eq(Admin::getEnabled, true));
    }

    @Override
    public List<Role> getRolesByAdminId(Integer adminId) {
        return roleService.getRolesByAdminId(adminId);
    }
}
