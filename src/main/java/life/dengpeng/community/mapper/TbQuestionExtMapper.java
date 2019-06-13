package life.dengpeng.community.mapper;

import life.dengpeng.community.model.TbQuestion;

import java.util.List;

public interface TbQuestionExtMapper {

    int incView(TbQuestion record);

    void incComment(TbQuestion record);

    List<TbQuestion> selectByTag(TbQuestion record);
}