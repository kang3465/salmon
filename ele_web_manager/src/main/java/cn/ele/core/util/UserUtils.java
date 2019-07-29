package cn.ele.core.util;

import cn.ele.core.pojo.user.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by sang on 2017/12/30.
 */
public class UserUtils {
    public static User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
