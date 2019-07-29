package cn.ele.core.config;

import cn.ele.core.entity.RespBean;
import com.alibaba.fastjson.JSON;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class WebAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest req,
                                        HttpServletResponse resp,
                                        AuthenticationException e) throws IOException {
        resp.setContentType("application/json;charset=utf-8");
        RespBean respBean = null;
        if (e instanceof BadCredentialsException ||
                e instanceof UsernameNotFoundException) {
            respBean = RespBean.error("账户名或者密码输入错误!");
        } else if (e instanceof LockedException) {
            respBean = RespBean.error("账户被锁定，请联系管理员!");
        } else if (e instanceof CredentialsExpiredException) {
            respBean = RespBean.error("密码过期，请联系管理员!");
        } else if (e instanceof AccountExpiredException) {
            respBean = RespBean.error("账户过期，请联系管理员!");
        } else if (e instanceof DisabledException) {
            respBean = RespBean.error("账户被禁用，请联系管理员!");
        } else {
            respBean = RespBean.error("登录失败!");
        }
        resp.setStatus(401);
        PrintWriter out = resp.getWriter();
        out.write(JSON.toJSONString(respBean));
        out.flush();
        out.close();
    }
}
