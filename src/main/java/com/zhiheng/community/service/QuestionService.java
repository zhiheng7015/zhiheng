package com.zhiheng.community.service;

import com.zhiheng.community.dto.PaginationDTO;
import com.zhiheng.community.dto.QuestionDTO;
import com.zhiheng.community.mapper.QuestionMapper;
import com.zhiheng.community.mapper.UserMapper;
import com.zhiheng.community.model.Question;
import com.zhiheng.community.model.QuestionExample;
import com.zhiheng.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取question信息和user信息 合并放入一个集合当中
 */
@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size) {
        System.out.println(page + size);

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        //总数
        Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        paginationDTO.setPagnation(totalPage, page);
        Integer offset = size * (page == 0 ? 0 : page - 1);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(),new RowBounds(offset,size));
        for (Question question : questions) {
            System.out.println(question + "拿数据库的数据。。。。。。");
        }
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //把questtion 对象放到questionDTO里边 （spring内置方法）
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestionDTOList(questionDTOList);
        System.out.println(paginationDTO);
        return paginationDTO;
    }

    //我的发布信息
    public PaginationDTO list(int userID, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        //总数
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userID);
        Integer totalCount = (int)questionMapper.countByExample(questionExample);
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        paginationDTO.setPagnation(totalPage, page);

        Integer offset = size * (page - 1);
        QuestionExample example=new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userID);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(),new RowBounds(offset,size));
        for (Question question : questions) {
            System.out.println(question + "拿数据库的数据。。。。。。");
        }
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //把questtion 对象放到questionDTO里边 （spring内置方法）
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestionDTOList(questionDTOList);
        System.out.println(paginationDTO);
        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        System.out.println("进来了2.........");
        Question question = questionMapper.selectByPrimaryKey(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }
    //根据id判断信息是否存在如多有进行更新 如果没有添加信息
    public void createORupdate(Question question) {
        Integer id=question.getId();
        if (id==null){
            //创建
            System.out.println(id);
            System.out.println("id=null创建");
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        }else {
            //更新
            Question updateQuestion=new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setTag(question.getTag());
            updateQuestion.setDescription(question.getDescription());
            QuestionExample questionExample=new QuestionExample();
            questionMapper.updateByExampleSelective(updateQuestion,questionExample);
        }
    }

    public void create(Question question) {
        //创建
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.insert(question);
    }
}
