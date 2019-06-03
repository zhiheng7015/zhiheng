package com.zhiheng.community.dto;

import com.zhiheng.community.model.User;
import lombok.Data;

/**
 * question
 *  user
 */
@Data
public class QuestionDTO {
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
    private User user;
}
