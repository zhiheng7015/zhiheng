package com.zhiheng.community.controller;

import com.zhiheng.community.dto.AccessTokenDTO;
import com.zhiheng.community.dto.GithubUser;
import com.zhiheng.community.mapper.UserMapper;
import com.zhiheng.community.model.User;
import com.zhiheng.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 *
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client_id}")
    private String clientId;
    @Value("${github.client_secret}")
    private String clientSecret;
    @Value("${github.redirect_uri}")
    private String redirecUri;
    @Autowired
    private  UserMapper userMapper;
    @GetMapping("/callback")
        public String callback(@RequestParam(name= "code") String code,
                               @RequestParam(name ="state") String state,
                               HttpServletRequest request,
                               HttpServletResponse response){
        AccessTokenDTO accessTokenDTO=new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(redirecUri);
        accessTokenDTO.setClient_secret(clientSecret);
        String accessToken=githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser=githubProvider.getuser(accessToken);
        if (githubUser!=null){
            User user=new User();
            String toKen = UUID.randomUUID().toString();
            System.out.println(toKen+"token值");
            user.setToKen(toKen);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            Cookie token = new Cookie("token", toKen);
            response.addCookie(token);
            //登录成功写cookie喝session
//            request.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        }else{
            //登录失败重新登录
            return "redirect:/";
        }
        }
}
