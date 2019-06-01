package life.dengpeng.community.dto;

import lombok.Data;

import java.io.Serializable;

/** github登录 响应参数

 */


@Data
public class AccessTokenDTO implements Serializable {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;


}
