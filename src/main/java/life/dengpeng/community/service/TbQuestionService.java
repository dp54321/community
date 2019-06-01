package life.dengpeng.community.service;

import life.dengpeng.community.dto.PageDTO;
import life.dengpeng.community.dto.QuestionDTO;
import life.dengpeng.community.enums.BJEnum;
import life.dengpeng.community.enums.IBJEnum;
import life.dengpeng.community.exception.BJException;
import life.dengpeng.community.mapper.TbQuestionMapper;
import life.dengpeng.community.mapper.TbUserMapper;

import life.dengpeng.community.model.TbQuestion;
import life.dengpeng.community.model.TbQuestionExample;
import life.dengpeng.community.model.TbUser;
import life.dengpeng.community.model.TbUserExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TbQuestionService {
    @Autowired
    private TbQuestionMapper tbQuestionMapper;

    @Autowired
    private TbUserMapper tbUserMapper;

    //查询全部记录
    public PageDTO findQuestionPage(Integer page, Integer size){
        PageDTO pageDTO = new PageDTO();
        Integer total = (int) tbQuestionMapper.countByExample(new TbQuestionExample());
        if(total != null && total >0) {
            setPageDTO(pageDTO, total, page, size);
            ArrayList<QuestionDTO> list = new ArrayList<>();
            int offset = (page - 1) * size;
            List<TbQuestion> tbQuestions = tbQuestionMapper.selectByExampleWithRowbounds(new TbQuestionExample(),new RowBounds(offset,size));
            questionToDTO(list, tbQuestions);
            pageDTO.setRows(list);
        }
        return pageDTO;
    }

    //根据用户查询自己的所有记录
    public PageDTO findQuestionByUserId(Long userId,Integer page, Integer size){
        PageDTO pageDTO = new PageDTO();
        TbQuestionExample example = new TbQuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        Integer total = (int)tbQuestionMapper.countByExample(example);//个人总记录
        if(total != null && total >0) {
            setPageDTO(pageDTO, total, page, size);
            ArrayList<QuestionDTO> list = new ArrayList<>();
            TbQuestionExample example1 = new TbQuestionExample();
            example1.createCriteria().andCreatorEqualTo(userId);
            int offset = (pageDTO.getPage() - 1) * size;
            List<TbQuestion> tbQuestions = tbQuestionMapper.selectByExampleWithRowbounds(example1, new RowBounds(offset, size));
            questionToDTO(list, tbQuestions);
            pageDTO.setRows(list);
        }
        return pageDTO;
    }

    //赋值PageDTO 分页
    public PageDTO setPageDTO(PageDTO pageDTO,Integer total,Integer page,Integer size) {


        pageDTO.setTotal(total); //总记录
        if (total % size == 0) {   //总页数
            pageDTO.setTotalPage(total / size);
        } else {
            pageDTO.setTotalPage(total / size + 1);
        }

        if(page < 1){
            page = 1;
        }
        if(page > pageDTO.getTotalPage()){
            page = pageDTO.getTotalPage();
        }
        pageDTO.setPage(page);  //当前页
        List<Integer> pages = new ArrayList<>();
        pages.add(page);
        for(int i=1;i<=2;i++){
            if(page-i>0){   //向前追加两个页码
                pages.add(0,page-i);
            }
            if(page+i<=pageDTO.getTotalPage()){  //向后追加两个页码
                pages.add(page+i);
            }
        }

        pageDTO.setPages(pages);

        if (pageDTO.getPages().contains(1)) { //是否显示首页
            pageDTO.setFirstPage(false);
        } else {
            pageDTO.setFirstPage(true);
        }

        if (pageDTO.getPages().contains(pageDTO.getTotalPage())) {  //是否显示尾页
            pageDTO.setEndPage(false);
        } else {
            pageDTO.setEndPage(true);
        }

        if (page > 1) {  //是否显示上一页
            pageDTO.setLastPage(true);
        }
        if(page<pageDTO.getTotalPage()){  //是否显示下一页
            pageDTO.setNextPage(true);
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
                throw new BJException(BJEnum.QUESTION_ERROR);
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
            throw new BJException(BJEnum.QUESTION_ERROR);
        }
        TbUser tbUser = tbUserMapper.selectByPrimaryKey(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setTbUser(tbUser);
        BeanUtils.copyProperties(question,questionDTO);

        return questionDTO;

    }
}
