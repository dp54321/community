package life.dengpeng.community.dto;

import life.dengpeng.community.model.TbUser;
import lombok.Data;


@Data
public class ResponseCommentDTO {

    private Long id;

    private Long parentId;

    private Integer type;

    private String content;

    private Long commentator;

    private Long gmtCreate;

    private Long gmtModifled;

    private Long likeCount;

    private Long commentCount;

    private TbUser tbUser;
}
