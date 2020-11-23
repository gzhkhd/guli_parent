package com.atguigu.eduservice.listener;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

//此类未交给spring管理 需要new创建  不能使用spring注解注入其他对象
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    public EduSubjectService subjectService;

    public SubjectExcelListener() {}
    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    //读取excel内容，一行一行进行读取
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData == null) {
            throw new GuliException(20001, "读取文件数据为空");
        }
        //保存一级分类
        EduSubject eduOneSubject = existSubject(subjectService, subjectData.getOneSubjectName(), "0");
        if(eduOneSubject == null) {
            eduOneSubject = new EduSubject();
            eduOneSubject.setTitle(subjectData.getOneSubjectName());
            eduOneSubject.setParentId("0");
            subjectService.save(eduOneSubject);
        }

        //保存二级分类
        String parentId = eduOneSubject.getId();
        EduSubject eduTwoSubject = existSubject(subjectService, subjectData.getTwoSubjectName(), parentId);
        if(eduTwoSubject == null) {
            eduTwoSubject = new EduSubject();
            eduTwoSubject.setTitle(subjectData.getTwoSubjectName());
            eduTwoSubject.setParentId(parentId);
            subjectService.save(eduTwoSubject);
        }
    }

    //判断一级\二级分类不能重复
    public EduSubject existSubject(EduSubjectService subjectService, String name, String parentId) {
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", name);
        queryWrapper.eq("parent_id", parentId);
        EduSubject eduSubject = subjectService.getOne(queryWrapper);
        return eduSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
