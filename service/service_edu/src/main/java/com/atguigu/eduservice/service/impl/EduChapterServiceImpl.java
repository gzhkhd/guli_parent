package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-10-23
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideo(String courseId) {
        QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id", courseId);
        List<EduChapter> chapterList = baseMapper.selectList(chapterWrapper);

        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id", courseId);
        List<EduVideo> videoList = videoService.list(videoWrapper);

        //数据返回
        List<ChapterVo> chapterVoList = new ArrayList<>();
        for (int i = 0; i < chapterList.size(); i++) {
            EduChapter chapter = chapterList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);

            ArrayList<VideoVo> videoVoList = new ArrayList<>();
            for (int f = 0; f < videoList.size(); f++) {
                EduVideo eduVideo = videoList.get(f);
                if(eduVideo.getChapterId().equals(chapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoVoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoList);
            chapterVoList.add(chapterVo);
        }
        return chapterVoList;
    }

    @Override
    public EduChapter getChapterById(String chapterId) {
        return baseMapper.selectById(chapterId);
    }

    @Override
    public Boolean removeChapterById(String chapterId) {
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("chapter_id", chapterId);
        int count = videoService.count(videoQueryWrapper);
        if(count > 0) {
           throw new GuliException(20001, "该课程章节下存在课程小节，删除失败");
        } else {
            int result = baseMapper.deleteById(chapterId);
            return result > 0;
        }
    }

    @Override
    public Boolean saveChapter(EduChapter eduChapter) {
        int result = baseMapper.insert(eduChapter);
        return result > 0;
    }

    @Override
    public Boolean updateChapterById(EduChapter eduChapter) {
        int result = baseMapper.updateById(eduChapter);
        return result > 0;
    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", courseId);
        baseMapper.delete(chapterQueryWrapper);
    }

}
