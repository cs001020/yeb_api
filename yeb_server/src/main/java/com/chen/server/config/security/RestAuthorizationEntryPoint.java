package com.chen.server.config.security;

import com.alibaba.fastjson2.JSON;
import com.chen.server.pojo.Results;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 未登陆或者token失效的时，自定义的返回结果
 *
 * @author CHEN
 * @date 2022/09/29
 */
@Component
public class RestAuthorizationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        Results results = Results.error("尚未登陆");
        results.setCode(401);
        PrintWriter out = response.getWriter();
        out.write(JSON.toJSONString(results));
        out.flush();
        out.close();
    }
}
