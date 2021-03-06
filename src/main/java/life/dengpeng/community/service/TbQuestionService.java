package life.dengpeng.community.service;

import life.dengpeng.community.dto.PageDTO;
import life.dengpeng.community.dto.QuestionDTO;
import life.dengpeng.community.enums.BJEnum;
import life.dengpeng.community.exception.BJException;
import life.dengpeng.community.mapper.TbCommentMapper;
import life.dengpeng.community.mapper.TbQuestionExtMapper;

import life.dengpeng.community.mapper.TbQuestionMapper;
import life.dengpeng.community.mapper.TbUserMapper;
import life.dengpeng.community.model.TbQuestion;
import life.dengpeng.community.model.TbQuestionExample;
import life.dengpeng.community.model.TbUser;
import life.dengpeng.community.model.TbUserExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TbQuestionService {
    @Autowired
    private TbQuestionMapper tbQuestionMapper;

    @Autowired
    private TbUserMapper tbUserMapper;

    @Autowired
    private TbQuestionExtMapper tbQuestionExtMapper;

    @Autowired
    private TbCommentMapper tbCommentMapper;

    //查询全部记录
    public PageDTO findQuestionPage(Integer page, Integer size){
        Integer total = (int) tbQuestionMapper.countByExample(new TbQuestionExample());
        PageDTO pageDTO = PageDTO.setPageDTO(total, page, size);
        if(total != null && total >0) {

            ArrayList<QuestionDTO> list = new ArrayList<>();
            int offset = (page - 1) * size;
            TbQuestionExample example = new TbQuestionExample();
            example.setOrderByClause("gmt_create desc");
            List<TbQuestion> tbQuestions = tbQuestionMapper.selectByExampleWithRowbounds(example,new RowBounds(offset,size));
            questionToDTO(list, tbQuestions);
            pageDTO.setRows(list);
        }
        return pageDTO;
    }

    //根据用户查询自己的所有记录
    public PageDTO findQuestionByUserId(Long userId,Integer page, Integer size){
        TbQuestionExample example = new TbQuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        Integer total = (int)tbQuestionMapper.countByExample(example);//个人总记录
        PageDTO pageDTO = PageDTO.setPageDTO(total, page, size);
        if(total != null && total >0) {
            ArrayList<QuestionDTO> list = new ArrayList<>();
            TbQuestionExample example1 = new TbQuestionExample();
            example1.createCriteria().andCreatorEqualTo(userId);
            example1.setOrderByClause("gmt_create desc");
            int offset = (page - 1) * size;
            List<TbQuestion> tbQuestions = tbQuestionMapper.selectByExampleWithRowbounds(example1, new RowBounds(offset, size));
            questionToDTO(list, tbQuestions);
            pageDTO.setRows(list);
        }
        return pageDTO;
    }



    //将类转换成DTO
    public void questionToDTO(ArrayList<QuestionDTO> list, List<TbQuestion> questionList) {
        if(questionList != null && questionList.size()>0){
            for (TbQuestion tbQuestion : questionList) {
                QuestionDTO questionDTO = new QuestionDTO();

                TbUserExample example = new TbUserExample();
                example.createCriteria().andUidEqualTo(tbQuestion.getCreator());
                TbUser tbUser = tbUserMapper.selectByPrimaryKey(tbQuestion.getCreator());
                BeanUtils.copyProperties(tbQuestion,questionDTO);
                questionDTO.setTbUser(tbUser);
                list.add(questionDTO);
            }
        }
    }

    /**
     * 增加和修改问题
     * @param tbQuestion
     */
    public void saveOrUpdate(TbQuestion tbQuestion) {

        if(tbQuestion.getId() != null && tbQuestion.getId()>0){
            TbQuestion question = new TbQuestion();
            question.setId(tbQuestion.getId());
            question.setTitle(tbQuestion.getTitle());
            question.setDescription(tbQuestion.getDescription());
            question.setTag(tbQuestion.getTag());
            question.setGmtModifled(System.currentTimeMillis());
            int i = tbQuestionMapper.updateByPrimaryKeySelective(question);
            if(i != 1){
                throw new BJException(BJEnum.QUESTION_NOT_FOUND);
            }
        }else {
            tbQuestionMapper.insert(tbQuestion);
        }

    }


    /**
     * 查询问题数据
     * @param id
     * @return
     */
    public QuestionDTO findQuestionById(Long id) {

        TbQuestion question = tbQuestionMapper.selectByPrimaryKey(id);
        if(question == null){
            throw new BJException(BJEnum.QUESTION_NOT_FOUND);
        }
        TbUser tbUser = tbUserMapper.selectByPrimaryKey(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setTbUser(tbUser);
        BeanUtils.copyProperties(question,questionDTO);

        return questionDTO;

    }

    //增加阅读数
    public void incView(Long id) {

        TbQuestion updateQuestion = new TbQuestion();
        updateQuestion.setId(id);
        updateQuestion.setViewCount(1);
        tbQuestionExtMapper.incView(updateQuestion);
    }

    //根据标签查询相关问题
    public List<QuestionDTO> selectByTag(QuestionDTO questionDTO) {
        TbQuestion question = new TbQuestion();
        question.setId(questionDTO.getId());
        String tag = questionDTO.getTag();
        if(StringUtils.isBlank(tag)){
                return new ArrayList<>();
        }
        String replace = StringUtils.replace(tag, ",", "|");
        question.setTag(replace);
        List<TbQuestion> tbQuestions = tbQuestionExtMapper.selectByTag(question);
        List<QuestionDTO> collect = tbQuestions.stream().map(q -> {
            QuestionDTO questionDTO1 = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO1);
            return questionDTO1;
        }).collect(Collectors.toList());


        return collect;
    }
}
