package com.chen.server.config.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chen.server.mapper.AdminMapper;
import com.chen.server.pojo.Admin;
import com.chen.server.service.AdminService;
import com.chen.server.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * security配置
 *
 * @author CHEN
 * @date 2022/09/29
 */
@Configuration
public class SecurityConfig {
    @Resource
    private AdminMapper adminMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    @Autowired
    private RestAuthorizationEntryPoint restAuthorizationEntryPoint;
    @Autowired
    private CustomFilter customFilter;
    @Autowired
    private CustomUrlDecisionManager customUrlDecisionManager;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        //放行静态资源 登陆接口 验证码接口
        return (web) -> web.ignoring().antMatchers(
                "/css/**",
                "/js/**",
                "/index.html",
                "favicon.ico",
                "/doc.html/**",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/api-docs/**",
                "/login",
                "/captcha");
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //使用JWT 不需要csrf
        http.csrf().disable()
                //不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 所有请求都要求认证
                .anyRequest()
                .authenticated()
                // 动态权限配置
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setAccessDecisionManager(customUrlDecisionManager);
                        object.setSecurityMetadataSource(customFilter);
                        return object;
                    }
                })
                .and()
                //禁用缓存
                .headers()
                .cacheControl();
        //登陆jwt 登陆授权过滤器
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        //添加自定义 未登陆未授权处理器
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthorizationEntryPoint);

        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Admin admin = adminMapper.selectOne(new LambdaQueryWrapper<Admin>()
                    .eq(Admin::getUsername, username).
                    eq(Admin::getEnabled, true));
            if (null != admin) {
                admin.setRoles(roleService.getRolesByAdminId(admin.getId()));
                return admin;
            }
            throw new UsernameNotFoundException("用户名或者密码错误");
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
