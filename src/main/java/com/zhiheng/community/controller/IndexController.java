package com.zhiheng.community.controller;

import com.zhiheng.community.mapper.UserMapper;
import com.zhiheng.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/")
    public String index(HttpServletRequest request){
        System.out.println("登录方法");
        Cookie[] cookies = request.getCookies();
        System.out.println(cookies+"cookies集合");
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")){
                String token=cookie.getValue();
                System.out.println(token+"token值");
                User user=userMapper.findByToken(token);
                System.out.println(user.getName()+"名字");
                if (user!=null){
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }
    return "index";
    }

}
