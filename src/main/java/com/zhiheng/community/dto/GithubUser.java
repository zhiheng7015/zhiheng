package com.zhiheng.community.dto;

import lombok.Data;

/***
 * user信息
 */
@Data
public class GithubUser {
    private  String name;
    private  long id;
    private  String bio;
    private String avatar_url;
}
