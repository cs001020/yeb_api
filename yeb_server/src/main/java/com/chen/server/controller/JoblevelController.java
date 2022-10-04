package com.chen.server.controller;


import com.chen.server.pojo.Joblevel;
import com.chen.server.pojo.Results;
import com.chen.server.service.JoblevelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author CHEN
 * @since 2022-09-29
 */
@RestController
@RequestMapping("/system/basic/joblevel")
public class JoblevelController {

    @Autowired
    private JoblevelService joblevelService;

    @ApiOperation(value = "获取所有职称")
    @GetMapping("/")
    public Results getAllJobLevel() {
        return Results.success("success", joblevelService.list());
    }

    @ApiOperation(value = "添加职称")
    @PostMapping("/")
    public Results addJobLevel(@RequestBody Joblevel joblevel) {
        joblevel.setCreatedate(LocalDateTime.now());
        if (joblevelService.save(joblevel)) {
            return Results.success("添加成功");
        }
        return Results.error("添加失败");
    }

    @ApiOperation(value = "更新职称信息")
    @PutMapping("/")
    public Results updateJobLevel(@RequestBody Joblevel joblevel) {
        if (joblevelService.updateById(joblevel)) {
            return Results.success("更新成功");
        }
        return Results.error("更新失败");
    }

    @ApiOperation(value = "删除职称信息")
    @DeleteMapping("/{id}")
    public Results deleteJobLevel(@PathVariable("id") Integer id) {
        if (joblevelService.removeById(id)) {
            return Results.success("删除成功");
        }
        return Results.error("删除失败");
    }

    @ApiOperation(value = "批量删除职称信息")
    @DeleteMapping("/")
    public Results deleteJobLevelsByIds(Integer[] ids) {
        if (joblevelService.removeByIds(Arrays.asList(ids))) {
            return Results.success("删除成功");
        }
        return Results.error("删除失败");
    }

}

