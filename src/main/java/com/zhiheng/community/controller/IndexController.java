package com.zhiheng.community.controller;

import com.zhiheng.community.dto.PaginationDTO;
import com.zhiheng.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "1")Integer page,
                        @RequestParam(name = "size",defaultValue = "5")Integer size
                        ){
        //发布完成返回到首页
        PaginationDTO pagination = questionService.list(page, size);
        System.out.println(pagination+"aaaaaaaaaaaaaaaaa");
        model.addAttribute("pagination",pagination);
        return "index";
    }

}
