package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.Ret;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-10-09
 */
@Api(value = "讲师管理", tags = "讲师管理")
@CrossOrigin
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public Ret findAllTeacher (){
        List<EduTeacher> list = eduTeacherService.list(null);
        return Ret.ok().data("items", list);
    }

    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("/removeTeacher/{id}")
    public Ret removeTeacher(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){
        boolean flag = eduTeacherService.removeById(id);
        if (!flag){
            return Ret.error();
        }
        return Ret.ok();
    }

    /**
     *  分页带参数查询讲师列表
     * @param current  当前页
     * @param limit  每页记录数
     * @param teacherQuery
     * @return
     */
    @ApiOperation(value = "带参分页查询")
    @PostMapping("pageTeacher/{current}/{limit}")
    public Ret pageListTeacher(@ApiParam(name = "current", value = "当前页码", required = true)
                               @PathVariable long current,
                               @ApiParam(name = "limit", value = "每页记录数", required = true)
                               @PathVariable long limit,
                               @ApiParam(name = "teacherQuery", value = "查询对象", required = false)
                               @RequestBody(required = false) TeacherQuery teacherQuery) {
        Page<EduTeacher> pageParam = new Page<>(current, limit);
        eduTeacherService.pageQuery(pageParam, teacherQuery);
        //数据list集合
        List<EduTeacher> records = pageParam.getRecords();
        //总记录数
        long total = pageParam.getTotal();
        return Ret.ok().data("total", total).data("rows", records);
    }

    //添加讲师数据
    @ApiOperation(value = "添加讲师信息")
    @PostMapping("addTeacher")
    public Ret save(
            @ApiParam(name = "eduTeacher", value = "讲师信息", required = true)
            @RequestBody(required = true) EduTeacher eduTeacher) {
        boolean flag = eduTeacherService.save(eduTeacher);
        if (!flag){
            return Ret.error();
        }
        return Ret.ok();
    }

    //根据id查询
    @ApiOperation(value = "根据id查询")
    @GetMapping("getById/{id}")
    public Ret getById(@ApiParam(name = "id", value = "讲师ID", required = true)
                           @PathVariable String id){
        EduTeacher teacher = eduTeacherService.getById(id);
        return Ret.ok().data("teacher", teacher);
    }

    //根据id修改
    @ApiOperation(value = "根据ID修改讲师")
    @PostMapping("updateById")
    public Ret updateById(@ApiParam(name = "eduTeacher", value = "讲师实体", required = true)
                          @RequestBody EduTeacher eduTeacher){
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if (!flag){
            return Ret.error();
        }
        return Ret.ok();
    }

}

