package life.dengpeng.community.controller;

import life.dengpeng.community.service.TbNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @author dp
 * @create 2019-06-23 12:15
 */
@Controller
public class NotifyController {

    @Autowired
    private TbNotifyService tbNotifyService;

    @GetMapping("/notify/{notifyId}")
    public String updateNotifyStatus(@PathVariable(name="notifyId") Long id){

        Long questionId = tbNotifyService.updateNotifyStatus(id);

        return "redirect:/question/"+questionId;
    }

}
