package cn.ele.core.service;

import cn.ele.core.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class UserDetailServiceImpl implements UserDetailsService {

    UserService userService;


    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        cn.ele.core.pojo.user.User user = null;
        try {
            user = userService.findOneByUserName(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (user.getStatus().equals("0")){
            throw new UsernameNotFoundException("用户处于非正常状态");
        }else if (user == null) {
            return null;
        }else{
            //创建权限集合
            List<GrantedAuthority> grantedList = new ArrayList<>();
            //向权限集合中添加权限
            grantedList.add(new SimpleGrantedAuthority("ROLE_USER"));
            return new User(username,user.getPassword(),grantedList);
        }
    }

}
