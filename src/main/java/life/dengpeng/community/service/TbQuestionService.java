package life.dengpeng.community.service;

import life.dengpeng.community.dto.PageDTO;
import life.dengpeng.community.dto.QuestionDTO;
import life.dengpeng.community.mapper.TbQuestionMapper;
import life.dengpeng.community.mapper.UserMapper;
import life.dengpeng.community.model.TbQuestion;
import life.dengpeng.community.model.TbUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dp
 * @create 2019-05-24 18:47
 */
@Service
public class TbQuestionService {
    @Autowired
    private TbQuestionMapper tbQuestionMapper;

    @Autowired
    private UserMapper userMapper;

    //查询全部记录
    public PageDTO findQuestionPage(Integer page, Integer size){
        PageDTO pageDTO = new PageDTO();
        Integer total = tbQuestionMapper.findQuestionCount(); //总记录
        if(total != null && total >0) {
            setPageDTO(pageDTO, total, page, size);
            ArrayList<QuestionDTO> list = new ArrayList<>();
            List<TbQuestion> questionList = tbQuestionMapper.findQuestionAll((pageDTO.getPage() - 1) * size, size);
            questionToDTO(list, questionList);
            pageDTO.setRows(list);
        }
        return pageDTO;
    }



    //赋值PageDTO
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



    //根据用户查询自己的所有记录
    public PageDTO findQuestionByUserId(Long userId,Integer page, Integer size){
        PageDTO pageDTO = new PageDTO();
        Integer total = tbQuestionMapper.findQuestionCountByUserId(userId); //个人总记录
        if(total != null && total >0) {
            setPageDTO(pageDTO, total, page, size);
            ArrayList<QuestionDTO> list = new ArrayList<>();
            List<TbQuestion> questionList = tbQuestionMapper.findQuestionByUserId(userId,(pageDTO.getPage() - 1) * size, size);
            questionToDTO(list, questionList);
            pageDTO.setRows(list);
        }
        return pageDTO;
    }

    //将类转换成DTO
    public void questionToDTO(ArrayList<QuestionDTO> list, List<TbQuestion> questionList) {
        if(questionList != null && questionList.size()>0){
            for (TbQuestion tbQuestion : questionList) {
                QuestionDTO questionDTO = new QuestionDTO();
                TbUser user = userMapper.findUserByid(tbQuestion.getCreator());
                BeanUtils.copyProperties(tbQuestion,questionDTO);
                questionDTO.setTbUser(user);
                list.add(questionDTO);
            }
        }
    }


}
