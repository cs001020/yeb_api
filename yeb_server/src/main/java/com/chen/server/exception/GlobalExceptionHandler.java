package com.chen.server.exception;

import com.chen.server.pojo.Results;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理程序
 *
 * @author CHEN
 * @date 2022/10/04
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLException.class)
    public Results sqlException(SQLException e){
        if (e instanceof SQLIntegrityConstraintViolationException){
            return Results.error("该数据有管理数据，删除失败");
        }
        return Results.error("数据库异常，操作失败");
    }
}
