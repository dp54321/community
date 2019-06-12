package life.dengpeng.community.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class CommentDTO implements Serializable {

    private Long parentId;

    private Integer type; //1 评论问题  2 回复评论

    private String content;

    private Long commentator;
}
