package com.zhiheng.community.controller;

import com.zhiheng.community.dto.QuestionDTO;
import com.zhiheng.community.mapper.QuestionMapper;
import com.zhiheng.community.mapper.UserMapper;
import com.zhiheng.community.model.Question;
import com.zhiheng.community.model.User;
import com.zhiheng.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 发布信息 edit更新
 */
@Controller
public class PublishController {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id")Integer id,
                       Model model){
        QuestionDTO question = questionService.getById(id);
        System.out.println("进入编辑问题方法"+question);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        return "publish";
    }
    @GetMapping("/publish")
    public  String  publish(){
        return  "publish";
    }
    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "title",required = false) String title,
                            @RequestParam(value = "description",required = false) String description,
                            @RequestParam(value = "tag",required =false) String tag,
                            @RequestParam(value = "id",required = false)Integer id,
                            HttpServletRequest request,
                            Model model){
        System.out.println(id+"id-----qusetionID");
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        //判断发布内容是否有空
        if (title==null || title==""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        } if (description==null || description==""){
            model.addAttribute("error","内容不能为空");
            return "publish";
        } if (tag==null || tag==""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        //判断是否登录 若没有登录不能发布信息
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        //发布信息的获取
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        if (id==null){
            questionService.create(question);
        }else {
            question.setId(id);
            questionService.createORupdate(question);
        }
        return "redirect:/";
    }
}
