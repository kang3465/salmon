package cn.ele.core.controller.menu;

import cn.ele.core.pojo.user.Menu;
import cn.ele.core.pojo.user.User;
import cn.ele.core.service.MenuService;
import cn.ele.core.service.user.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.security.Principal;
import java.util.List;

/**
 * @author kang
 */
@RestController
@RequestMapping("menu")
public class MenuController {
    @Reference
    MenuService menuService;
    @Reference
    UserService userService;
    @RequestMapping("queryMenu")
    public List<Menu> queryMenu(HttpServletRequest request, HttpServletResponse response) {
        JSONArray objects =null;
                SecurityContextImpl securityContextImpl = (SecurityContextImpl) request
                .getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) securityContextImpl.getAuthentication().getPrincipal();
        String username = principal.getUsername();
        /**
         * 获取当前登录对象（用户名密码以及权限）
         */
        /*UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        String username = userDetails.getUsername();*/
        List<Menu> menuList =null;
        try {
            User user = userService.findOneByUserName(username);
            menuList = menuService.queryMenuByUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return menuList;
    }

}
