package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-10-23
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 获取课程基本信息
     * @param courseId
     * @return
     */
    CourseInfoVo getCourseIdInfo(String courseId);

    /**
     * 修改课程基本信息
     * @param courseInfoVo
     */
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 根据courseId获取课程发布信息
     * @param courseId
     * @return
     */
    CoursePublishVo getCoursePublishInfo(String courseId);

    boolean publishCourse(String courseId);

    /**
     * 分页带参查询课程列表
     * @param coursePage
     * @param courseQuery
     */
    void pageQuery(Page<EduCourse> coursePage, CourseQuery courseQuery);

    /**
     * 删除课程
     * @param courseId
     * @return
     */
    boolean removeCourse(String courseId);
}
