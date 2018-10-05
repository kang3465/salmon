package cn.ele.core.controller.user;

import cn.ele.core.entity.PageResult;
import cn.ele.core.entity.Result;
import cn.ele.core.pojo.user.User;
import cn.ele.core.service.user.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * @author kang
 */
@RestController
@RequestMapping("user")
public class UserContraller {
    @Reference
    UserService userService;

    @RequestMapping("Login")
    public String login() {
        return "success";
    }

    @RequestMapping("Regist")
    @ResponseBody
    public Result regist(String userName, String email, String password, String phoneNumber) {

        User user = new User();
        user.setUsername(userName);
        user.setEmail(email);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setPhone(phoneNumber);
        try {
            if (userService.addUser(user)==-1) {
                throw new Exception("用户已存在");
            }
        } catch (Exception e) {
            e.printStackTrace();

            return new Result(false,"注册失败："+e.getMessage());
        }

        return new Result(true,"注册成功");
    }

    /**
     * 检查用户名是否被注册过
     * @param username 需要检查的username字符串（需要已经校验过格式的username）
     * @return 返回boolean类型
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

    /**
     * 检查email是否注册过
     * @param email 需要检查的email字符串（需要已经校验过格式的email）
     * @return 返回boolean类型
     */
    @RequestMapping("checkEmailRepeat")
    public boolean checkEmailRepeat(String email){

        User user = userService.findOneByEmail(email);
        return user == null;
    }

    /**
     * 分页查询用户列表并返回分页结果
     * @param pageNum 展示第pageNum页结果
     * @param pageSize 每页展示pageSize条数据
     * @return 返回分页数据
     */
    @RequestMapping(value = "queryUserListSafeByPage", method= RequestMethod.GET)
    public PageResult queryUserListSafeByPage(Integer pageNum, Integer pageSize){
        return userService.queryAllUserListSafeByPage(pageNum, pageSize);
    }

}
