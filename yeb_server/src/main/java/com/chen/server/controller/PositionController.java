package com.chen.server.controller;


import com.chen.server.pojo.Position;
import com.chen.server.pojo.Results;
import com.chen.server.service.PositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author CHEN
 * @since 2022-09-29
 */
@RestController
@RequestMapping("/system/basic/pos")
public class PositionController {
    @Autowired
    private PositionService positionService;

    @ApiOperation(value = "获取所有职位信息")
    @GetMapping("/")
    public Results getAllPositions() {
        return Results.success("success", positionService.list());
    }

    @ApiOperation(value = "添加职位信息")
    @PostMapping("/")
    public Results addPosition(@RequestBody Position position) {
        position.setCreatedate(LocalDateTime.now());
        if (positionService.save(position)) {
            return Results.success("添加成功");
        }
        return Results.error("添加失败");
    }

    @ApiOperation(value = "更新职位信息")
    @PutMapping("/")
    public Results updatePosition(@RequestBody Position position) {
        if (positionService.updateById(position)) {
            return Results.success("更新成功");
        }
        return Results.error("更新失败");
    }

    @ApiOperation(value = "删除职位信息")
    @DeleteMapping("/{id}")
    public Results deletePosition(@PathVariable("id") Integer id) {
        if (positionService.removeById(id)) {
            return Results.success("删除成功");
        }
        return Results.error("删除失败");
    }

    @ApiOperation(value = "批量删除职位信息")
    @DeleteMapping("/")
    public Results deletePositionsByIds(Integer[] ids) {
        if (positionService.removeByIds(Arrays.asList(ids))) {
            return Results.success("删除成功");
        }
        return Results.error("删除失败");
    }
}

