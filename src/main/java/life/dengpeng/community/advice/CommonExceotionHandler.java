package life.dengpeng.community.advice;

import ch.qos.logback.core.net.SyslogOutputStream;
import life.dengpeng.community.exception.BJException;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


@ControllerAdvice
public class CommonExceotionHandler {

    @ExceptionHandler(BJException.class)
    public ModelAndView handException(BJException e,Model model){

        model.addAttribute("message",e.getMessage());
        return new ModelAndView("error");
    }

}
