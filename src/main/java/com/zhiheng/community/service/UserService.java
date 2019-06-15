package com.zhiheng.community.service;

import com.zhiheng.community.mapper.UserMapper;
import com.zhiheng.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 判断user用户是否存在
 */
@Service
public class UserService {
@Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        //判断当前user是否存在
       User dbuser=userMapper.findByAccountID(user.getAccountId());
        System.out.println(dbuser+"1111111111111"+dbuser);
        //user的话在添加进行修改
       if (dbuser==null){
           System.out.println("进入插入。。。。。。。。");
           //插入
           user.setGmtCreate(System.currentTimeMillis());
           user.setGmtModified(user.getGmtCreate());
           userMapper.insert(user);
       }else {
           //更新
           System.out.println("进入user更新"+user);
           dbuser.setGmtModified(System.currentTimeMillis());
           dbuser.setAvatarUrl(user.getAvatarUrl());
           dbuser.setName(user.getName());
           dbuser.setToKen(user.getToKen());
           userMapper.updateUser(dbuser);
       }
    }
}
