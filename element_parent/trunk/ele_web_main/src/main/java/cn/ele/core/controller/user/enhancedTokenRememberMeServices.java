package cn.ele.core.controller.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

public class enhancedTokenRememberMeServices extends TokenBasedRememberMeServices {
    public enhancedTokenRememberMeServices(String key, UserDetailsService userDetailsService) {
        super(key, userDetailsService);
    }
}
