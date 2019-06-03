package com.zhiheng.community.mapper;

        import com.zhiheng.community.model.Question;
        import org.apache.ibatis.annotations.Insert;
        import org.apache.ibatis.annotations.Mapper;
        import org.apache.ibatis.annotations.Param;
        import org.apache.ibatis.annotations.Select;

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
}
