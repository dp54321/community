package life.dengpeng.community.mapper;

import java.util.List;
import life.dengpeng.community.model.TbTag;
import life.dengpeng.community.model.TbTagExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TbTagMapper {
    long countByExample(TbTagExample example);

    int deleteByExample(TbTagExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbTag record);

    int insertSelective(TbTag record);

    List<TbTag> selectByExampleWithRowbounds(TbTagExample example, RowBounds rowBounds);

    List<TbTag> selectByExample(TbTagExample example);

    TbTag selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbTag record, @Param("example") TbTagExample example);

    int updateByExample(@Param("record") TbTag record, @Param("example") TbTagExample example);

    int updateByPrimaryKeySelective(TbTag record);

    int updateByPrimaryKey(TbTag record);
}