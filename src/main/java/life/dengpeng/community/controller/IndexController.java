package life.dengpeng.community.controller;

import life.dengpeng.community.dto.PageDTO;
import life.dengpeng.community.service.TbQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dp
 * @create 2019-05-18 21:54
 */
@Controller
public class IndexController {


    @Autowired
    private TbQuestionService tbQuestionService;

    /**
     * 首页分页加载问题列表
     * @param httpServletRequest
     * @param model
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/")
    public String index(HttpServletRequest httpServletRequest,Model model,
                        @RequestParam(name = "page",defaultValue = "1")Integer page,
                        @RequestParam(name = "size",defaultValue = "3")Integer size){

        PageDTO pageDTO = tbQuestionService.findQuestionPage(page, size);
        model.addAttribute("pageDTO",pageDTO);

        return "index";
    }
}
