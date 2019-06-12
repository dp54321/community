package life.dengpeng.community.controller;

import life.dengpeng.community.dto.CommentDTO;
import life.dengpeng.community.dto.ResponseCommentDTO;
import life.dengpeng.community.dto.ResultDTO;
import life.dengpeng.community.enums.BJEnum;
import life.dengpeng.community.model.TbUser;
import life.dengpeng.community.service.TbCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
public class CommentController {


    @Autowired
    private TbCommentService tbCommentService;


    @PostMapping(value = "/comment")
    public Object addComment(@RequestBody CommentDTO commentDTO, HttpServletRequest request){

        TbUser user = (TbUser) request.getSession().getAttribute("user");
        if(user == null){
           return  ResultDTO.errorOf(BJEnum.NO_LOGIN);
        }
       commentDTO.setCommentator(3L);

        tbCommentService.insertComment(commentDTO);
        return ResultDTO.okOf();
    }

    @GetMapping("/comment/{id}")
    public Object findCommentById(@PathVariable(name="id") Long id){
        List<ResponseCommentDTO> commentDTOS = tbCommentService.findCommentByParentId(id, 2);
        return ResultDTO.okOf(commentDTOS);
    }


}
