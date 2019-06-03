package com.zhiheng.community.model;

import lombok.Data;

/**
 * user实体类
 */
@Data
public class User {
    private int id;
    private String name;
    private String accountId;
    private String toKen;
    private long gmtCreate;
    private long gmtModified;
    private String avatarUrl;
}
