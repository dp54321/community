package life.dengpeng.community.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dp
 * @create 2019-05-22 13:34
 */
@Data
public class TbUser implements Serializable {
    private Long uid;
    private String accountId;
    private String uname;
    private String token;
    private String bio;
    private Long gmtCreate;
    private Long gmtModiflde; //修改 modefiled
    private String avatarUrl;
}
