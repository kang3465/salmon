package cn.ele.core.controller.menu;

import cn.ele.core.pojo.user.Menu;
import cn.ele.core.pojo.user.User;
import cn.ele.core.service.MenuService;
import cn.ele.core.service.user.UserService;
import cn.ele.core.util.UserUtils;
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
    public List<Menu> queryMenu() {
        List<Menu> menuList =null;
        try {
            menuList = menuService.queryMenuByUser(UserUtils.getCurrentUser());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return menuList;
    }

}
