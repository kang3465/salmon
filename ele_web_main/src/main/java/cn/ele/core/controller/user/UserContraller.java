package cn.ele.core.controller.user;

import cn.ele.core.pojo.user.User;
import cn.ele.core.service.user.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserContraller {
    @Reference
    UserService userService;

    @RequestMapping("Login")
    public String Login() {
        return "success";
    }

    @RequestMapping("Regist")
    @ResponseBody
    public String regist(String userName, String email, String password, String phoneNumber) {
        User user = new User();
        user.setUsername(userName);
        user.setEmail(email);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setPhone(phoneNumber);
        try {
            userService.addUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "success";
    }

    /**
     * 检查用户名是否被注册过
     * @param username
     * @return
     */
    @RequestMapping("checkRepeat")
    public boolean checkRepeat(String username) {
        try {
            User user = userService.findOneByUserName(username);
            if (user != null) return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @RequestMapping("checkEmailRepeat")
    public boolean checkEmailRepeat(String email){

        User user = userService.findOneByEmail(email);
        if (user!=null)return false;
        return true;
    }

}