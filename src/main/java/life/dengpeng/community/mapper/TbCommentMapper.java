package life.dengpeng.community.mapper;

import java.util.List;
import life.dengpeng.community.model.TbComment;
import life.dengpeng.community.model.TbCommentExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TbCommentMapper {
    long countByExample(TbCommentExample example);

    int deleteByExample(TbCommentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbComment record);

    int insertSelective(TbComment record);

    List<TbComment> selectByExampleWithRowbounds(TbCommentExample example, RowBounds rowBounds);

    List<TbComment> selectByExample(TbCommentExample example);

    TbComment selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbComment record, @Param("example") TbCommentExample example);

    int updateByExample(@Param("record") TbComment record, @Param("example") TbCommentExample example);

    int updateByPrimaryKeySelective(TbComment record);

    int updateByPrimaryKey(TbComment record);
}