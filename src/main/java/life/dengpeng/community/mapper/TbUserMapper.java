package life.dengpeng.community.mapper;

import java.util.List;
import life.dengpeng.community.model.TbUser;
import life.dengpeng.community.model.TbUserExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TbUserMapper {
    long countByExample(TbUserExample example);

    int deleteByExample(TbUserExample example);

    int deleteByPrimaryKey(Long uid);

    int insert(TbUser record);

    int insertSelective(TbUser record);

    List<TbUser> selectByExampleWithRowbounds(TbUserExample example, RowBounds rowBounds);

    List<TbUser> selectByExample(TbUserExample example);

    TbUser selectByPrimaryKey(Long uid);

    int updateByExampleSelective(@Param("record") TbUser record, @Param("example") TbUserExample example);

    int updateByExample(@Param("record") TbUser record, @Param("example") TbUserExample example);

    int updateByPrimaryKeySelective(TbUser record);

    int updateByPrimaryKey(TbUser record);
}