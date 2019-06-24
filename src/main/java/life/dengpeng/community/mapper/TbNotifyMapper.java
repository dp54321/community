package life.dengpeng.community.mapper;

import java.util.List;
import life.dengpeng.community.model.TbNotify;
import life.dengpeng.community.model.TbNotifyExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TbNotifyMapper {
    long countByExample(TbNotifyExample example);

    int deleteByExample(TbNotifyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbNotify record);

    int insertSelective(TbNotify record);

    List<TbNotify> selectByExampleWithRowbounds(TbNotifyExample example, RowBounds rowBounds);

    List<TbNotify> selectByExample(TbNotifyExample example);

    TbNotify selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbNotify record, @Param("example") TbNotifyExample example);

    int updateByExample(@Param("record") TbNotify record, @Param("example") TbNotifyExample example);

    int updateByPrimaryKeySelective(TbNotify record);

    int updateByPrimaryKey(TbNotify record);
}