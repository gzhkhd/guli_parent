package com.atguigu.oss.controller;

import com.atguigu.commonutils.Ret;
import com.atguigu.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "阿里云文件管理", tags = "阿里云文件管理")
@CrossOrigin
@RestController
@RequestMapping("/eduoss/fileoss")
public class OssController {

    @Autowired
    private OssService ossService;

    @ApiOperation(value = "上传头像")
    @PostMapping("uploadFile")
    public Ret uploadOssFile(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file) {
        String url = ossService.uploadFileAvatar(file);
        return Ret.ok().message("文件上传成功").data("url", url);
    }

}
