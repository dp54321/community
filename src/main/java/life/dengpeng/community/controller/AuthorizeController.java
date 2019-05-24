package life.dengpeng.community.controller;

import life.dengpeng.community.dto.AccessTokenDTO;
import life.dengpeng.community.dto.GithubUser;
import life.dengpeng.community.mapper.UserMapper;
import life.dengpeng.community.model.TbUser;
import life.dengpeng.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

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
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                            @RequestParam(name = "state") String state,
                           HttpServletResponse httpServletResponse
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
            tbUser.setGmtModiflde(tbUser.getGmtCreate());
            tbUser.setAvatarUrl(githubUser.getAvatarUrl());
            //将用户信息写入数据库
            userMapper.insertUser(tbUser);
            // 把token写入cookie 用于登录认证
            Cookie cookie = new Cookie("token",token);
            httpServletResponse.addCookie(cookie);
            return "redirect:/";
        } else {
            //登录失败
            return "redirect:/";
        }
    }
}
