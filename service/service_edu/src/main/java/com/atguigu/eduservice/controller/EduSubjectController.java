package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.Ret;
import com.atguigu.eduservice.entity.subject.SubjectResult;
import com.atguigu.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-10-20
 */
@Api(value = "课程分类管理", tags = "课程分类管理")
@RestController
@CrossOrigin
@RequestMapping("/eduservice/subject")
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    @PostMapping("addSubject")
    @ApiOperation(value = "Excel批量导入")
    public Ret addSubject(MultipartFile file) {
        eduSubjectService.saveSubject(file, eduSubjectService);
        return Ret.ok().data("msg", "添加课程分类成功");
    }

    @GetMapping("subjectList")
    @ApiOperation(value = "课程分类列表")
    public Ret subjectList() {
        List<SubjectResult> subjectResultVo = eduSubjectService.subjectList();
        return Ret.ok().data("result", subjectResultVo);
    }

}

