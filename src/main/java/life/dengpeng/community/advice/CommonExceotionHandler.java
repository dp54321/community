package life.dengpeng.community.advice;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.alibaba.fastjson.JSON;
import life.dengpeng.community.dto.ResultDTO;
import life.dengpeng.community.enums.BJEnum;
import life.dengpeng.community.enums.IBJEnum;
import life.dengpeng.community.exception.BJException;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@ControllerAdvice
public class CommonExceotionHandler {

    @ExceptionHandler(BJException.class)
    public ModelAndView handException(BJException e, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

        String contentType = request.getContentType();
        if("application/json".equals(contentType)){
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            ResultDTO resultDTO;
            if(e == null) {
                resultDTO = ResultDTO.errorOf(BJEnum.SYS_ERROR);
            }else {
                resultDTO = ResultDTO.errorOf(e.getCode(),e.getMessage());
            }
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(resultDTO));
            writer.close();
            return null;
        }else{
            model.addAttribute("message",e.getMessage());
            return new ModelAndView("error");
        }

    }

}
