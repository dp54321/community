package life.dengpeng.community.controller;

import life.dengpeng.community.dto.QuestionDTO;
import life.dengpeng.community.dto.TagDTO;
import life.dengpeng.community.dto.UploadImageDTO;
import life.dengpeng.community.enums.BJEnum;
import life.dengpeng.community.exception.BJException;
import life.dengpeng.community.mapper.TbQuestionMapper;
import life.dengpeng.community.model.TbQuestion;
import life.dengpeng.community.model.TbUser;
import life.dengpeng.community.service.TbQuestionService;
import life.dengpeng.community.service.TbTagService;
import life.dengpeng.community.util.FastDFSClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class PublishController {

    @Autowired
    private TbQuestionService tbQuestionService;

    @Autowired
    private TbQuestionMapper tbQuestionMapper;

    @Autowired
    private TbTagService tbTagService;

    @Value("${FILE_SERVER_URL}")
    private String FILE_SERVER_URL;

    /**
     * 修改问题页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id")Long id,Model model){

        TbQuestion question = tbQuestionMapper.selectByPrimaryKey(id);
        List<TagDTO> allTag = tbTagService.findAllTag();
        model.addAttribute("tagDTOS",allTag);
        model.addAttribute("question",question);

        return "publish";

    }

    /**
     * 发布页面
     * @param model
     * @return
     */
    @RequestMapping("/publish")
    public String showPublish(Model model){
        List<TagDTO> allTag = tbTagService.findAllTag();
        model.addAttribute("tagDTOS",allTag);
        model.addAttribute("question",new QuestionDTO());
        return "publish";
    }

    /**
     * 添加发布问题和修改发布问题
     * @param tbQuestion
     * @param model
     * @param request
     * @return
     */
    @PostMapping("/publish")
    public String doPublish(TbQuestion tbQuestion, Model model, HttpServletRequest request){
        List<TagDTO> allTag = tbTagService.findAllTag();
        model.addAttribute("tagDTOS",allTag);
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
        if(tbQuestion.getTag() != null || !tbQuestion.getTag().equals("")){
            String invalid = tbTagService.filterInvalid(tbQuestion.getTag());
            if(!StringUtils.isBlank(invalid)){
                model.addAttribute("error",invalid+"非法标签");
                return "publish";
            }
        }


        TbUser user = (TbUser) request.getSession().getAttribute("user");
        if(user == null){
            throw new BJException(BJEnum.NO_LOGIN);
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



    @RequestMapping("/uploadImage")
    @ResponseBody
    public UploadImageDTO uploadImage(@RequestParam(value = "editormd-image-file")MultipartFile file){

        String filename = file.getOriginalFilename(); //获取文件名
        String extName = filename.substring(filename.lastIndexOf(".") + 1);
        UploadImageDTO uploadImageDTO = new UploadImageDTO();
        try {
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/fdfs_client.conf");
            String uploadFile = fastDFSClient.uploadFile(file.getBytes(), extName);
            uploadImageDTO.setSuccess(1);
            uploadImageDTO.setMessage("上传成功");
            uploadImageDTO.setUrl(FILE_SERVER_URL+uploadFile);
        } catch (Exception e) {
            uploadImageDTO.setSuccess(0);
            uploadImageDTO.setMessage("上传失败，正在修复，请稍后...");
            e.printStackTrace();
        }
        return uploadImageDTO;
    }





}
