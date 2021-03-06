package cn.ele.core.controller.user;

import cn.ele.core.entity.RespBean;
import cn.ele.core.entity.Result;
import cn.ele.core.pojo.user.User;
import cn.ele.core.service.user.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserContraller {
    @Reference
    UserService userService;

    @RequestMapping("/login_p")
    public RespBean login() {
        return RespBean.error("尚未登录，请登录！!");
    }
    @RequestMapping("Login")
    public String Login() {
        return "success";
    }

    @RequestMapping("Regist")
    @ResponseBody
    public RespBean regist(String userName, String email, String password, String phoneNumber) {
        User user = new User();
        user.setUsername(userName);
        user.setEmail(email);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setPhone(phoneNumber);
        try {
            if (userService.findOneByUserName(userName)!=null){
                return RespBean.error("用户已存在");
            }
            if (userService.addUser(user)==-1) {
                throw new Exception("用户已存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("注册失败：",e.getMessage());
        }
        return  RespBean.ok("注册成功！");
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
            if (user != null) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @RequestMapping("checkEmailRepeat")
    public boolean checkEmailRepeat(String email){

        User user = userService.findOneByEmail(email);
        if (user!=null) {
            return false;
        }
        return true;
    }

}
