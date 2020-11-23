package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.Ret;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-10-23
 */
@RestController
@CrossOrigin
@Api(value = "小节管理", tags = "小节管理")
@RequestMapping("/eduservice/video")
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @ApiOperation("增加小节")
    @PostMapping("saveVideoInfo")
    public Ret saveVideoInfo(@RequestBody EduVideo eduVideo) {
        boolean result = videoService.save(eduVideo);
        if(result) {
            return Ret.ok();
        }
        return Ret.error();
    }

    @ApiOperation("修改小节")
    @PostMapping("updateVideoInfoById")
    public Ret updateVideoInfoById(@RequestBody EduVideo eduVideo) {
        boolean result = videoService.updateById(eduVideo);
        if(result) {
            return Ret.ok();
        }
        return Ret.error();
    }

    @ApiOperation("根据id查询小节")
    @GetMapping("getVideoInfoById/{videoId}")
    public Ret getVideoInfoById(@PathVariable String videoId) {
        EduVideo video = videoService.getById(videoId);
        return Ret.ok().data("video", video);
    }

    @ApiOperation("根据id删除小节")
    @DeleteMapping("removeVideoById/{videoId}")
    public Ret removeVideoById(@PathVariable String videoId) {
        boolean result = videoService.removeById(videoId);
        if(result) {
            return Ret.ok();
        }
        return Ret.error();
    }

}

