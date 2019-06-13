package life.dengpeng.community.mapper;

import life.dengpeng.community.model.TbTag;
import life.dengpeng.community.model.TbTagExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface TbTagExtMapper {
   List<String> selectTagNamesAll();
}