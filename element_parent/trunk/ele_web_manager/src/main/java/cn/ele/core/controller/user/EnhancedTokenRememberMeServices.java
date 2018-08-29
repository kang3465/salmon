package cn.ele.core.controller.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

/**
 * @author kang
 */
public class EnhancedTokenRememberMeServices extends TokenBasedRememberMeServices {
    public EnhancedTokenRememberMeServices(String key, UserDetailsService userDetailsService) {
        super(key, userDetailsService);
    }
}
