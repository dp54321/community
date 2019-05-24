package life.dengpeng.community.service;

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

    public List<QuestionDTO> findQuestionAll(){
        ArrayList<QuestionDTO> list = new ArrayList<>();
        List<TbQuestion> questionList = tbQuestionMapper.findQuestionAll();
        if(questionList != null && questionList.size()>0){
            for (TbQuestion tbQuestion : questionList) {
                QuestionDTO questionDTO = new QuestionDTO();
                TbUser user = userMapper.findUserByid(tbQuestion.getCreator());
                BeanUtils.copyProperties(tbQuestion,questionDTO);
                questionDTO.setTbUser(user);
                list.add(questionDTO);
            }
        }
        return list;
    }



}
