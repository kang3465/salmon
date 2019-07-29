package cn.ele.core.config;

import cn.ele.core.entity.RespBean;
import com.alibaba.fastjson.JSON;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class WebLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
        resp.setContentType("application/json;charset=utf-8");
        RespBean respBean = null;
        respBean=RespBean.ok("注销成功");
        PrintWriter out = resp.getWriter();
        out.write(JSON.toJSONString(respBean));
        out.flush();
        out.close();
    }
}
