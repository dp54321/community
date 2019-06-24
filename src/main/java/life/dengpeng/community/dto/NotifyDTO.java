package life.dengpeng.community.dto;

import life.dengpeng.community.model.TbUser;
import lombok.Data;

/**
 * @author dp
 * @create 2019-06-23 11:12
 */
@Data
public class NotifyDTO {
    private TbUser tbUser;
    private String typeName;
    private String title;
    private Long notifyId;
    private Integer status;
}
