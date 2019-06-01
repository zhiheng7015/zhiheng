package com.zhiheng.community.service;

import com.zhiheng.community.dto.QuestionDTO;
import com.zhiheng.community.mapper.QuestionMapper;
import com.zhiheng.community.mapper.UserMapper;
import com.zhiheng.community.model.Question;
import com.zhiheng.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public List<QuestionDTO> list() {
        List<Question> questionList = questionMapper.list();
        List<QuestionDTO> questionDTOList =new  ArrayList<>();
        for (Question question:questionList){
           User user= userMapper.findById(question.getCreator());
            System.out.println(user.getId()+"ididiididid");
            for (Question question1:questionList){
                System.out.println(question1+",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,");
            }
           QuestionDTO questionDTO=new QuestionDTO();
           //把questtion 对象放到questionDTO里边 （spring内置方法）
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }

}
