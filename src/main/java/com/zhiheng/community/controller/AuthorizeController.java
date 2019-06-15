package com.zhiheng.community.controller;

import com.zhiheng.community.dto.AccessTokenDTO;
import com.zhiheng.community.dto.GithubUser;
import com.zhiheng.community.mapper.UserMapper;
import com.zhiheng.community.model.User;
import com.zhiheng.community.provider.GithubProvider;
import com.zhiheng.community.service.UserService;
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
 *获取登录信息
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
    private UserService userService;
    @GetMapping("/callback")
        public String callback(@RequestParam(name= "code") String code,
                               @RequestParam(name ="state") String state,
                               HttpServletRequest request,
                               HttpServletResponse response){
        //获取登录账号github的信息
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
            user.setAvatarUrl(githubUser.getAvatar_url());
            userService.createOrUpdate(user);
            Cookie token = new Cookie("token", toKen);
            response.addCookie(token);
//            request.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        }else{
            //登录失败重新登录
            return "redirect:/";
        }
        }
        //退出登录清楚session cookie
        @GetMapping("/logout")
        public String logout(HttpServletRequest request,
                             HttpServletResponse response){
            request.getSession().removeAttribute("user");
            Cookie cookie=new Cookie("token",null);
            cookie.setMaxAge(0);//马上删除
            response.addCookie(cookie);
            return "redirect:/";
        }
}
