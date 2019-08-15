package com.zhiheng.community.service;

import com.zhiheng.community.mapper.UserMapper;
import com.zhiheng.community.model.User;
import com.zhiheng.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 判断user用户是否存在
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        //判断当前user是否存在
        UserExample userExampl = new UserExample();
        userExampl.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExampl);
        System.out.println(users+"1111111111111"+users);
        //user的话在添加进行修改
       if (users.size()==0){
           System.out.println("进入插入。。。。。。。。");
           //插入
           user.setGmtCreate(System.currentTimeMillis());
           user.setGmtModified(user.getGmtCreate());
           userMapper.insert(user);
       }else {
           //更新
           User dbuser = users.get(0);
           User updateUser = new User();
           System.out.println("进入user更新"+user);
           updateUser.setGmtModified(System.currentTimeMillis());
           updateUser.setAvatarUrl(user.getAvatarUrl());
           updateUser.setName(user.getName());
           updateUser.setToken(user.getToken());
           UserExample exampl = new UserExample();
           exampl.createCriteria().andIdEqualTo(dbuser.getId());
           userMapper.updateByExampleSelective(updateUser,exampl);
       }
    }
}
