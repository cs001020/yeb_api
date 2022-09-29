package com.chen.server.config.security;

import com.alibaba.fastjson2.JSON;
import com.chen.server.pojo.Results;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 权限不足，自定义返回结果
 *
 * @author CHEN
 * @date 2022/09/29
 */
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        Results results = Results.error("权限不足，请联系管理员");
        results.setCode(401);
        PrintWriter out = response.getWriter();
        out.write(JSON.toJSONString(results));
        out.flush();
        out.close();
    }
}
