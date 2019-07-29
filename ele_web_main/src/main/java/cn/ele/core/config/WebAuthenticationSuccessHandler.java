package cn.ele.core.config;

import cn.ele.core.entity.RespBean;
import cn.ele.core.pojo.user.User;
import cn.ele.core.util.UserUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Service
public class WebAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    /**
     * 登录成功之后，返回前端之前的操作
     * @param req
     * @param resp
     * @param auth
     * @throws IOException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest req,
                                        HttpServletResponse resp,
                                        Authentication auth) throws IOException {
        resp.setContentType("application/json;charset=utf-8");
        User currentUser = UserUtils.getCurrentUser();
        User user = new User();
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        user.setNickName(currentUser.getNickName());
        user.setName(currentUser.getName());
        user.setHeadPic(currentUser.getHeadPic());
        RespBean respBean = RespBean.ok("登录成功!", user);
        PrintWriter out = resp.getWriter();
        out.write(JSON.toJSONString(respBean));
        out.flush();
        out.close();
    }
}
