package com.zhiheng.community.mapper;

import com.zhiheng.community.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    //获取登录信息添加到数据库当中
    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified,avatar_url) values(#{name},#{accountId},#{toKen},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);
    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);
    //获取当前账号信息
    @Select("select * from user where id=#{id}")
    User findById(@Param("id") Integer id);
    //判断user是否存在
    @Select("select * from user where account_id=#{accountId}")
    User findByAccountID(String accountId);
    //user的话在添加进行修改
    @Update("update user set name=#{name},token=#{toKen},gmt_modified=#{gmtModified},avatar_url=#{avatarUrl} where id=#{id}")
    void updateUser(User user);
}
