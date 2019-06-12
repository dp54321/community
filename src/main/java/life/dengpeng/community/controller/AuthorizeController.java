package life.dengpeng.community.controller;

import life.dengpeng.community.dto.AccessTokenDTO;
import life.dengpeng.community.dto.GithubUser;
import life.dengpeng.community.model.TbUser;
import life.dengpeng.community.provider.GithubProvider;
import life.dengpeng.community.service.TbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


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
    @Autowired
    private TbUserService tbUserService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                            @RequestParam(name = "state") String state,
                           HttpServletResponse httpServletResponse,
                           @RequestParam(value="redirect_url",required = false) String redirect_url
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
            //登录成功
            TbUser tbUser = new TbUser();
            tbUser.setAccountId(String.valueOf(githubUser.getId()));
            tbUser.setUname(githubUser.getName());
            tbUser.setBio(githubUser.getBio());
            String token = UUID.randomUUID().toString();
            tbUser.setToken(token);
            tbUser.setGmtCreate(System.currentTimeMillis());
            tbUser.setAvatarUrl(githubUser.getAvatarUrl());
            //将用户信息写入数据库
            tbUserService.saveOrUpdate(tbUser);
            // 把token写入cookie 用于登录认证
            Cookie cookie = new Cookie("token",token);
            httpServletResponse.addCookie(cookie);
            if(redirect_url != null ){
                return "forward:"+redirect_url;
            }
            return "redirect:/";
        } else {
            //登录失败
            return "redirect:/";
        }
    }
}
