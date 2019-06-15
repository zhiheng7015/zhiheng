package com.zhiheng.community.controller;

import com.zhiheng.community.dto.QuestionDTO;
import com.zhiheng.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 文章详情
 */
@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name="id")Integer id,
                           Model model){
        System.out.println("进来了。。。。。。。。。。。");
        QuestionDTO questionDTO= questionService.getById(id);
        System.out.println(questionDTO);
        model.addAttribute("question",questionDTO);
        return "question";
    }
}
