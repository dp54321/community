package life.dengpeng.community.interceptor;

import life.dengpeng.community.mapper.TbUserMapper;
import life.dengpeng.community.model.TbUser;
import life.dengpeng.community.model.TbUserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author dp
 * @create 2019-05-27 20:32
 */
@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private TbUserMapper tbUserMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length>=0) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    TbUserExample example = new TbUserExample();
                    example.createCriteria().andTokenEqualTo(token);
                    List<TbUser> tbUsers = tbUserMapper.selectByExample(example);
                    if (tbUsers != null && tbUsers.size()>0) {
                        request.getSession().setAttribute("user", tbUsers.get(0));
                        break;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
