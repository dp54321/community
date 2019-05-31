package life.dengpeng.community.dto;

import life.dengpeng.community.model.TbUser;
import lombok.Data;

import java.io.Serializable;

/**
 * 问题参数
 * @author dp
 * @create 2019-05-24 18:44
 */
@Data
public class QuestionDTO implements Serializable {
    private Long id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModifled;
    private Long creator;  //发表人
    private Integer commentCount; //'评论数',
    private Integer viewCount;//浏览数',
    private Integer likeCount; //点赞数',
    private String tag;
    private TbUser tbUser;
}
