package life.dengpeng.community.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;

/**
 * @author dp
 * @create 2019-05-24 14:36
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TbQuestion implements Serializable {
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


}
