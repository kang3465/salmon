package cn.ele.core.controller.menu;

import cn.ele.core.pojo.user.Menu;
import cn.ele.core.pojo.user.User;
import cn.ele.core.service.MenuService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

@RestController
@RequestMapping("menu")
public class MenuController {
    @Reference
    MenuService menuService;

    @RequestMapping("queryMenu")
    public JSONArray queryMenu(HttpServletRequest request, HttpServletResponse response) {
//        SecurityContextImpl securityContextImpl = (SecurityContextImpl) getSession().getAttribute("SPRING_SECURITY_CONTEXT");
//        HttpSession servletContext = (HttpSession) ContextLoader.getCurrentWebApplicationContext().getServletContext();
        JSONArray objects =null;
                SecurityContextImpl securityContextImpl = (SecurityContextImpl) request
                .getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        List<Menu> menuList =null;
        try {
            menuList = menuService.queryMenuByUser(new User());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (menuList!=null){
            objects = JSON.parseArray(JSON.toJSONString(menuList));

        }
        return objects;
    }

}
