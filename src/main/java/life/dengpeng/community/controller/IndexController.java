package life.dengpeng.community.controller;

import jdk.nashorn.internal.parser.Token;
import life.dengpeng.community.dto.PageDTO;
import life.dengpeng.community.dto.QuestionDTO;
import life.dengpeng.community.mapper.UserMapper;
import life.dengpeng.community.model.TbUser;
import life.dengpeng.community.service.TbQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author dp
 * @create 2019-05-18 21:54
 */
@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TbQuestionService tbQuestionService;

    @GetMapping("/")
    public String index(HttpServletRequest httpServletRequest,Model model,
                        @RequestParam(name = "page",defaultValue = "1")Integer page,
                        @RequestParam(name = "size",defaultValue = "3")Integer size){
        Cookie[] cookies = httpServletRequest.getCookies();
        if(cookies != null && cookies.length>=0) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    TbUser tbUser = userMapper.findUserByToken(token);
                    if (tbUser != null) {
                        httpServletRequest.getSession().setAttribute("user", tbUser);
                        break;
                    }
                }
            }
        }
        PageDTO pageDTO = tbQuestionService.findQuestionPage(page, size);
        model.addAttribute("pageDTO",pageDTO);

        return "index";
    }
}
