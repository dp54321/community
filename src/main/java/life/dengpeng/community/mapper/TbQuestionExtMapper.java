package life.dengpeng.community.mapper;

import life.dengpeng.community.model.TbQuestion;

public interface TbQuestionExtMapper {

    int incView(TbQuestion record);

    void incComment(TbQuestion record);
}