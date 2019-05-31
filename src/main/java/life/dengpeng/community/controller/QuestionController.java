package life.dengpeng.community.controller;

import life.dengpeng.community.dto.QuestionDTO;
import life.dengpeng.community.mapper.TbQuestionMapper;
import life.dengpeng.community.model.TbQuestion;
import life.dengpeng.community.service.TbQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author dp
 * @create 2019-05-31 13:47
 */
@Controller
public class QuestionController {

    @Autowired
    private TbQuestionService tbQuestionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,Model model){

        QuestionDTO question = tbQuestionService.findQuestionById(id);
        model.addAttribute("question",question);
        return "question";
    }



}
