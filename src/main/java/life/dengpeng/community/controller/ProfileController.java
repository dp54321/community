package life.dengpeng.community.controller;

import life.dengpeng.community.dto.PageDTO;
import life.dengpeng.community.model.TbUser;
import life.dengpeng.community.service.TbNotifyService;
import life.dengpeng.community.service.TbQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


@Controller
public class ProfileController {

    @Autowired
    private TbQuestionService tbQuestionService;
    @Autowired
    private TbNotifyService tbNotifyService;


    /**
     * 个人详情页面
     * @param action
     * @param page
     * @param size
     * @param httpServletRequest
     * @param model
     * @return
     */
    @GetMapping("/profile/{action}")
    public String findQuestionByUserId(@PathVariable(name = "action") String action,
                                       @RequestParam(name = "page",defaultValue = "1")Integer page,
                                       @RequestParam(name = "size",defaultValue = "3")Integer size,
                                        HttpServletRequest httpServletRequest, Model model){

        TbUser user = (TbUser)httpServletRequest.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/";
        }
        if("questions".equals(action)){
            PageDTO pageQuestionDTO = tbQuestionService.findQuestionByUserId(user.getUid(), page, size);
            model.addAttribute("pageDTO",pageQuestionDTO);
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的问题");

        }else if("repies".equals(action)){
            PageDTO pageNotifyDTO = tbNotifyService.findNotifyByReceiver(user.getUid(), page, size);
            model.addAttribute("pageDTO",pageNotifyDTO);
            model.addAttribute("section","repies");
            model.addAttribute("sectionName","最新回复");
        }

        return "profile";
    }

}
