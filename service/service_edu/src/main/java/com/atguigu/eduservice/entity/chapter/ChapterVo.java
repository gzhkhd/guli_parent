package com.atguigu.eduservice.entity.chapter;

import lombok.Data;

import java.util.List;

@Data
public class ChapterVo {

    private String id;

    private String title;

    //小节集合
    private List<VideoVo> children;
}
