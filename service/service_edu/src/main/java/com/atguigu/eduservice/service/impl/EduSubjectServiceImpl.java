package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.SubjectResult;
import com.atguigu.eduservice.entity.subject.SubjectVo;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-10-20
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001,"添加课程分类失败");
        }
    }

    @Override
    public List<SubjectResult> subjectList() {
        //写法一
        //数据返回
        List<SubjectResult> subjectResultList = new ArrayList<>();
        //获取一级分类数据
        QueryWrapper<EduSubject> oneQueryWrapper = new QueryWrapper<>();
        oneQueryWrapper.eq("parent_id", "0");
        List<EduSubject> oneEduSubjectList = baseMapper.selectList(oneQueryWrapper);
        //获取二级分类数据
        QueryWrapper<EduSubject> twoQueryWrapper = new QueryWrapper<>();
        twoQueryWrapper.ne("parent_id", 0);
        List<EduSubject> twoEduSubjectList = baseMapper.selectList(twoQueryWrapper);

        //填充一级分类数据
        for (int i = 0; i < oneEduSubjectList.size(); i++) {
            EduSubject oneSubject = oneEduSubjectList.get(i);
            //创建一级类别对象
            SubjectResult subjectResult = new SubjectResult();
            BeanUtils.copyProperties(oneSubject, subjectResult);
            subjectResultList.add(subjectResult);
            //填充二级分类vo数据
            List<SubjectVo> subjectVoList = new ArrayList<>();
            for (int j = 0; j < twoEduSubjectList.size(); j++) {
                EduSubject twoSubject = twoEduSubjectList.get(j);
                if(oneSubject.getId().equals(twoSubject.getParentId())) {
                    //创建二级类别vo对象
                    SubjectVo subjectVo = new SubjectVo();
                    BeanUtils.copyProperties(twoSubject, subjectVo);
                    subjectVoList.add(subjectVo);
                }
            }
            subjectResult.setChildren(subjectVoList);
        }
        return subjectResultList;
        
       /* //写法二
        //数据返回
        List<SubjectResult> subjectResultList = new ArrayList<>();
        //获取一级分类数据
        QueryWrapper<EduSubject> oneQueryWrapper = new QueryWrapper<>();
        oneQueryWrapper.eq("parent_id", "0");
        List<EduSubject> oneEduSubjectList = baseMapper.selectList(oneQueryWrapper);

        QueryWrapper<EduSubject> twoQueryWrapper = null;
        List<EduSubject> twoEduSubjectList = null;
        for (int i = 0; i < oneEduSubjectList.size(); i++) {
            SubjectResult subjectResult = new SubjectResult();
            //一级列表数据
            EduSubject oneEduSubject = oneEduSubjectList.get(i);
            twoQueryWrapper = new QueryWrapper<>();
            twoQueryWrapper.eq("parent_id", oneEduSubject.getId());
            twoEduSubjectList = baseMapper.selectList(twoQueryWrapper);
            BeanUtils.copyProperties(oneEduSubject, subjectResult);
            //把二级列表集合拷贝至List<SubjectVo>
            List<SubjectVo> subjectVoList = copyList(twoEduSubjectList);
            subjectResult.setId(oneEduSubject.getId());
            subjectResult.setTitle(oneEduSubject.getTitle());
            subjectResult.setChildren(subjectVoList);
            subjectResultList.add(subjectResult);
        }
        return subjectResultList;*/
    }

    //对象集合拷贝至对象集合
    private List<SubjectVo> copyList(List<EduSubject> twoEduSubjectList) {
        List<SubjectVo> subjectVoList = new ArrayList<>();
        EduSubject eduSubject = null;
        if(twoEduSubjectList != null) {
            for (int i = 0; i < twoEduSubjectList.size(); i++) {
                eduSubject = twoEduSubjectList.get(i);
                SubjectVo subjectVo = new SubjectVo();
                BeanUtils.copyProperties(eduSubject, subjectVo);
                subjectVoList.add(subjectVo);
            }
        }
        return subjectVoList;
    }
}
