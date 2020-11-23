package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-10-23
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if(insert < 1) {
            throw new GuliException(20001, "添加课程信息失败");
        }
        //添加课程详情
        String courseId = eduCourse.getId();
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseId);
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.save(eduCourseDescription);
        return courseId;
    }

    @Override
    public CourseInfoVo getCourseIdInfo(String courseId) {
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        EduCourse eduCourse = baseMapper.selectById(courseId);
        BeanUtils.copyProperties(eduCourse, courseInfoVo);

        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if(update < 1) {
            throw new GuliException(20001, "修改课程信息失败");
        }
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfoVo.getId());
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.updateById(eduCourseDescription);
    }

    @Override
    public CoursePublishVo getCoursePublishInfo(String courseId) {
        return baseMapper.getPublishCourseInfo(courseId);
    }

    @Override
    public boolean publishCourse(String courseId) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus(EduCourse.COURSE_NORMAL);
        int count = baseMapper.updateById(eduCourse);
        return count > 0;
    }

    @Override
    public void pageQuery(Page<EduCourse> coursePage, CourseQuery courseQuery) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");
        if(courseQuery == null) {
            baseMapper.selectPage(coursePage, queryWrapper);
            return;
        }
        if (!StringUtils.isEmpty(courseQuery.getTitle())){
            queryWrapper.like("title", courseQuery.getTitle());
        }
        if(!StringUtils.isEmpty(courseQuery.getStatus())) {
            queryWrapper.eq("status", courseQuery.getStatus());
        }
        if (!StringUtils.isEmpty(courseQuery.getTeacherId()) ) {
            queryWrapper.eq("teacher_id", courseQuery.getTeacherId());
        }
        if (!StringUtils.isEmpty(courseQuery.getSubjectParentId())) {
            queryWrapper.ge("subject_parent_id", courseQuery.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseQuery.getSubjectId())) {
            queryWrapper.ge("subject_id", courseQuery.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseQuery.getBegin())) {
            queryWrapper.ge("gmt_create", courseQuery.getBegin());
        }
        if (!StringUtils.isEmpty(courseQuery.getEnd())) {
            queryWrapper.le("gmt_create", courseQuery.getEnd());
        }
        baseMapper.selectPage(coursePage, queryWrapper);
    }

    @Override
    public boolean removeCourse(String courseId) {
        //1.根据课程id删除小节
        eduVideoService.removeVideoByCourseId(courseId);
        //2.根据课程id删除章节
        eduChapterService.removeChapterByCourseId(courseId);
        //3.根据课程id删除描述
        eduCourseDescriptionService.removeDescriptionByCourseId(courseId);
        //4.根据课程id删除课程本身
        int count = baseMapper.deleteById(courseId);
        return count > 0;
    }

}
