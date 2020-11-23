package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.Ret;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-10-23
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/course")
@Api(value = "课程管理", tags = "课程管理")
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    /**
     * 分页带参查询课程列表
     * @param current
     * @param limit
     * @param courseQuery
     * @return
     */
    @ApiOperation("分页带参查询课程列表")
    @PostMapping("pageCourse/{current}/{limit}")
    public Ret pageCourse(@PathVariable long current,
                          @PathVariable long limit,
                          @RequestBody CourseQuery courseQuery) {
        Page<EduCourse> coursePage = new Page<>(current, limit);
        eduCourseService.pageQuery(coursePage, courseQuery);
        List<EduCourse> records = coursePage.getRecords();
        long total = coursePage.getTotal();
        return Ret.ok().data("total", total).data("rows", records);
    }

    @PostMapping("addCourseInfo")
    @ApiOperation(value = "添加课程及基本信息")
    public Ret addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String courseId = eduCourseService.saveCourseInfo(courseInfoVo);
        return Ret.ok().data("courseId", courseId);
    }

    @ApiOperation("获取课程基本信息")
    @GetMapping("getCourseIdInfo/{courseId}")
    public Ret getCourseIdInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = eduCourseService.getCourseIdInfo(courseId);
        return Ret.ok().data("courseInfoVo", courseInfoVo);
    }

    @ApiOperation("修改课程基本信息")
    @PostMapping("updateCourseInfo")
    public Ret updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        eduCourseService.updateCourseInfo(courseInfoVo);
        return Ret.ok();
    }

    @ApiOperation(value = "根据courseId获取课程发布信息")
    @GetMapping("coursePublishInfo/{courseId}")
    public Ret coursePublishInfo(@PathVariable String courseId){
        CoursePublishVo result = eduCourseService.getCoursePublishInfo(courseId);
        return Ret.ok().data("result", result);
    }

    @ApiOperation(value = "课程发布")
    @GetMapping("publishCourse/{courseId}")
    public Ret publishCourse(@PathVariable String courseId){
        boolean flag = eduCourseService.publishCourse(courseId);
        if(flag) {
            return Ret.ok();
        }
        return Ret.error();
    }

    @ApiOperation(value = "删除课程")
    @DeleteMapping("deleteCourse/{courseId}")
    public Ret deleteCourse(@PathVariable String courseId) {
        boolean flag = eduCourseService.removeCourse(courseId);
        if(flag) {
            return Ret.ok();
        }
        return Ret.error();
    }
}

