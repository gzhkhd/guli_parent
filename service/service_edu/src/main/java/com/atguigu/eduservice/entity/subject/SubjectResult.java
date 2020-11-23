package com.atguigu.eduservice.entity.subject;

import lombok.Data;

import java.util.List;

@Data
public class SubjectResult {

    private String id;

    private String title;

    private List<SubjectVo> children;
}
