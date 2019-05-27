package life.dengpeng.community.mapper;

import life.dengpeng.community.model.TbQuestion;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author dp
 * @create 2019-05-24 14:41
 */
@Mapper
public interface TbQuestionMapper {

    @Insert("insert into tb_question (title,description,gmt_create,gmt_modifled,creator,comment_count,view_count,like_count,tag)" +
            "values(#{title},#{description},#{gmtCreate},#{gmtModifled},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    void insertPublish(TbQuestion tbPublish);

    @Select("select id,title,description,gmt_create,gmt_modifled,creator,comment_count,view_count,like_count,tag from tb_question " +
            "limit #{page},#{size} ")
    List<TbQuestion> findQuestionAll(@Param(value = "page") Integer page, @Param(value = "size") Integer size);


    @Select("select count(1) from tb_question")
    Integer findQuestionCount();


}
