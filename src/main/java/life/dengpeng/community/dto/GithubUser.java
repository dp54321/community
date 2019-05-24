package life.dengpeng.community.dto;

import lombok.Data;

/**
 * @author dp
 * @create 2019-05-21 11:20
 */
@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatarUrl;
}
