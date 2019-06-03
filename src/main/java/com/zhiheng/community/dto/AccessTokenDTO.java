package com.zhiheng.community.dto;

import lombok.Data;

/**
 *git信息
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
