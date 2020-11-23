package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourseDescription;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程简介 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-10-23
 */
public interface EduCourseDescriptionService extends IService<EduCourseDescription> {

    /**
     * 根据课程id删除描述
     * @param courseId
     */
    void removeDescriptionByCourseId(String courseId);
}
