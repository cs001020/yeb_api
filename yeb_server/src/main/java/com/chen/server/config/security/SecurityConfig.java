package com.chen.server.config.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chen.server.mapper.AdminMapper;
import com.chen.server.pojo.Admin;
import com.chen.server.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
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
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    @Autowired
    private RestAuthorizationEntryPoint restAuthorizationEntryPoint;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //使用JWT 不需要csrf
        http.csrf().disable()
                //不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //允许登陆访问
                .antMatchers("/login","/captcha").permitAll()
                //放行静态资源
                .and()
                .authorizeRequests()
                .antMatchers("/css/**",
                        "/js/**",
                        "/index.html",
                        "favicon.ico",
                        "/doc.html",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/v2/api-docs/**").permitAll()
                //除了登陆请求都需要认证
                .anyRequest()
                .authenticated()
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
                return admin;
            }
            return null;
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
