package life.dengpeng.community.controller;

import life.dengpeng.community.dto.QuestionDTO;
import life.dengpeng.community.mapper.TbQuestionMapper;
import life.dengpeng.community.model.TbQuestion;
import life.dengpeng.community.model.TbUser;
import life.dengpeng.community.service.TbQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dp
 * @create 2019-05-24 13:55
 */
@Controller
public class PublishController {

    @Autowired
    private TbQuestionService tbQuestionService;

    @Autowired
    private TbQuestionMapper tbQuestionMapper;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id")Long id,Model model){

        TbQuestion question = tbQuestionMapper.selectByPrimaryKey(id);
        model.addAttribute("question",question);

        return "publish";

    }

    @RequestMapping("/publish")
    public String showPublish(Model model){
        model.addAttribute("question",new QuestionDTO());
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(TbQuestion tbQuestion, Model model, HttpServletRequest request){
        model.addAttribute("title",tbQuestion.getTitle());
        model.addAttribute("description",tbQuestion.getDescription());
        model.addAttribute("tag",tbQuestion.getTag());
        model.addAttribute("question",tbQuestion);
        if(tbQuestion.getTitle() == null || tbQuestion.getTitle().equals("")){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(tbQuestion.getDescription() == null || tbQuestion.getDescription().equals("")){
            model.addAttribute("error","内容不能为空");
            return "publish";
        }
        if(tbQuestion.getTag() == null || tbQuestion.getTag().equals("")){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        TbUser user = (TbUser) request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/";
        }
        tbQuestion.setCreator(user.getUid());
        tbQuestion.setGmtCreate(System.currentTimeMillis());
        tbQuestion.setGmtModifled(tbQuestion.getGmtCreate());
        tbQuestion.setCommentCount(0);
        tbQuestion.setLikeCount(0);
        tbQuestion.setViewCount(0);
        tbQuestionService.saveOrUpdate(tbQuestion);
        return "redirect:/";
    }








}
