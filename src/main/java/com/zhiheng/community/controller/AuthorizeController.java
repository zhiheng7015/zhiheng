package com.zhiheng.community.controller;

import com.zhiheng.community.dto.AccessTokenDTO;
import com.zhiheng.community.dto.GithubUser;
import com.zhiheng.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @GetMapping("/callback")
        public String callback(@RequestParam(name= "code") String code,
                               @RequestParam(name ="state") String state){
        AccessTokenDTO accessTokenDTO=new AccessTokenDTO();
        accessTokenDTO.setClient_id("28ab53dd9edeba5540ee");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri("http://localhost:8887/callback");
        accessTokenDTO.setClient_secret("bf444a206131682e7538860da63a79d77cb5c44e");
        String accessToken=githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user=githubProvider.getuser(accessToken);
        System.out.println(user.getName());
            return  "index";
        }
}
