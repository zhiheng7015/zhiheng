package com.zhiheng.community.controller;
/**
 * 个人中心profileController
 */

import com.zhiheng.community.dto.PaginationDTO;
import com.zhiheng.community.mapper.UserMapper;
import com.zhiheng.community.model.User;
import com.zhiheng.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class profileController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          @RequestParam(name = "page",defaultValue = "1")Integer page,
                          @RequestParam(name = "size",defaultValue = "5")Integer size,
                          Model model,
                          HttpServletRequest request){
        //判断是否登录
        User user=null;
        Cookie[] cookies = request.getCookies();
        if (cookies!=null && cookies.length!=0){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")){
                    String token=cookie.getValue();
                    user=userMapper.findByToken(token);
                    if (user!=null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        if (user==null){
            return "redirect:/";
        }
        if ("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的问题");
        } if ("repies".equals(action)){
            model.addAttribute("section","repies");
            model.addAttribute("sectionName","最新回复");
        }
        PaginationDTO pagination = questionService.list(user.getId(), page, size);
        model.addAttribute("pagination",pagination);
        return "profile";
    }
}
