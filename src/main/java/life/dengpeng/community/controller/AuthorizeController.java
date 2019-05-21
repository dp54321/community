package life.dengpeng.community.controller;

import life.dengpeng.community.dto.AccessTokenDTO;
import life.dengpeng.community.dto.GithubUser;
import life.dengpeng.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dp
 * @create 2019-05-21 10:45
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientID;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirectUri}")
    private String redirectURI;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                            @RequestParam(name = "state") String state,
                           HttpServletRequest httpServletRequest
                            ) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientID);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectURI);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getGithubUser(accessToken);
        if (githubUser != null) {
            //登录成功 用户信息保存到session中
            httpServletRequest.getSession().setAttribute("user", githubUser);
            return "redirect:/";
        } else {
            //登录失败
            return "redirect:/";
        }
    }
}
