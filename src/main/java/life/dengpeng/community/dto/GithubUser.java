package life.dengpeng.community.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * github用户信息参数

 */
@Data
public class GithubUser implements Serializable {
    private String name;
    private Long id;
    private String bio;
    private String avatarUrl;
}
