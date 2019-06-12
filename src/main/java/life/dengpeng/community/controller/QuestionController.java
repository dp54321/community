package life.dengpeng.community.controller;

import life.dengpeng.community.dto.QuestionDTO;
import life.dengpeng.community.dto.ResponseCommentDTO;
import life.dengpeng.community.service.TbCommentService;
import life.dengpeng.community.service.TbQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Controller
public class QuestionController {

    @Autowired
    private TbQuestionService tbQuestionService;


    @Autowired
    private TbCommentService tbCommentService;

    /**
     * 问题详情数据
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,Model model){

        QuestionDTO question = tbQuestionService.findQuestionById(id);

        List<ResponseCommentDTO> commentDTOS = tbCommentService.findCommentByParentId(id,1);
        tbQuestionService.incView(id);

        model.addAttribute("question",question);
        model.addAttribute("comments",commentDTOS);
        return "question";
    }



}
