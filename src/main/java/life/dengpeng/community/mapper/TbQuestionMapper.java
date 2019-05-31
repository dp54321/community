package life.dengpeng.community.mapper;

import java.util.List;
import life.dengpeng.community.model.TbQuestion;
import life.dengpeng.community.model.TbQuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TbQuestionMapper {
    long countByExample(TbQuestionExample example);

    int deleteByExample(TbQuestionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbQuestion record);

    int insertSelective(TbQuestion record);

    List<TbQuestion> selectByExampleWithBLOBsWithRowbounds(TbQuestionExample example, RowBounds rowBounds);

    List<TbQuestion> selectByExampleWithBLOBs(TbQuestionExample example);

    List<TbQuestion> selectByExampleWithRowbounds(TbQuestionExample example, RowBounds rowBounds);

    List<TbQuestion> selectByExample(TbQuestionExample example);

    TbQuestion selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbQuestion record, @Param("example") TbQuestionExample example);

    int updateByExampleWithBLOBs(@Param("record") TbQuestion record, @Param("example") TbQuestionExample example);

    int updateByExample(@Param("record") TbQuestion record, @Param("example") TbQuestionExample example);

    int updateByPrimaryKeySelective(TbQuestion record);

    int updateByPrimaryKeyWithBLOBs(TbQuestion record);

    int updateByPrimaryKey(TbQuestion record);
}