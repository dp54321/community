package life.dengpeng.community.mapper;

import life.dengpeng.community.model.TbComment;
import life.dengpeng.community.model.TbCommentExample;
import life.dengpeng.community.model.TbQuestion;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface TbCommentExtMapper {

    void incComment(TbComment record);
}