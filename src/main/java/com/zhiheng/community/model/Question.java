package com.zhiheng.community.model;

import lombok.Data;

/**
 * question实体类
 */
@Data
public class Question {
    private int id;
    private String title;
    private String description;
    private String tag;
    private long gmtCreate;
    private long gmtModified;
    private Integer creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
}
