package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.Ret;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
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
@RestController
@CrossOrigin
@Api(value = "章节管理", tags = "章节管理")
@RequestMapping("/eduservice/chapter")
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    @ApiOperation("获取课程大纲")
    @GetMapping("getChapterVideo/{courseId}")
    public Ret getChapterVideo(@PathVariable String courseId) {
        List<ChapterVo> result = chapterService.getChapterVideo(courseId);
        return Ret.ok().data("result", result);
    }

    @ApiOperation("查询章节")
    @GetMapping("getChapterById/{chapterId}")
    public Ret getChapterById(@PathVariable String chapterId) {
        EduChapter eduChapter = chapterService.getChapterById(chapterId);
        return Ret.ok().data("eduChapter", eduChapter);
    }

    @ApiOperation("删除")
    @DeleteMapping("removeChapterById/{chapterId}")
    public Ret removeChapterById(@PathVariable String chapterId) {
        Boolean flag = chapterService.removeChapterById(chapterId);
        if(flag) {
            return Ret.ok();
        }
        return Ret.error();
    }

    @ApiOperation("保存")
    @PostMapping("saveChapter")
    public Ret saveChapter(@RequestBody EduChapter eduChapter) {
        Boolean flag = chapterService.saveChapter(eduChapter);
        if(flag) {
            return Ret.ok();
        }
        return Ret.error();
    }

    @ApiOperation("修改")
    @PostMapping("updateChapterById")
    public Ret updateChapterById(@RequestBody EduChapter eduChapter) {
        Boolean flag = chapterService.updateChapterById(eduChapter);
        if(flag) {
            return Ret.ok();
        }
        return Ret.error();
    }
}

