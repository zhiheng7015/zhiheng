package com.zhiheng.community.mapper;

import com.zhiheng.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * question接口
 */
@Mapper
public interface QuestionMapper {
    //发布信息方法接口
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag)values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    public void create(Question question);

    //页面显示分页数据
    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    //查总数
    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator=#{userID} limit #{offset},#{size}")
    List<Question> listByUserID(@Param(value = "userID") Integer userID, @Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from question where creator=#{userID}")
    Integer countByUserID(@Param(value = "userID") Integer userID);

    @Select("select * from question where id=#{id}")
    Question getById(@Param(value = "id")Integer id);
    @Update("update question set title=#{title}, description=#{description},tag=#{tag} where id=#{id}")
    void update(Question question);
}
