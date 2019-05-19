package life.dengpeng.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author dp
 * @create 2019-05-18 21:54
 */
@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello(@RequestParam(name = "name",defaultValue = "word") String name, Model model){
        model.addAttribute("name",name);
        return "hello";
    }
}
