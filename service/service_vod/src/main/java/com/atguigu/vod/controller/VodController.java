package com.atguigu.vod.controller;

import com.atguigu.commonutils.Ret;
import com.atguigu.vod.service.VodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "小节视频上传", tags = "小节视频上传")
@CrossOrigin
@RestController
@RequestMapping("/eduvod/video")
public class VodController {

    @Autowired
    private VodService vodService;

    @ApiOperation(value = "上传视频")
    @PostMapping("uploadVideoAly")
    public Ret uploadVideoAly(MultipartFile file) {
        String videoId = vodService.uploadVideoAly(file);
        return Ret.ok().data("videoId", videoId);
    }
}
