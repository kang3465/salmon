package cn.ele.core.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class UserDetailServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        cn.ele.core.pojo.user.User user = new cn.ele.core.pojo.user.User();
        user.setStatus("username");
        System.out.println(username);
        user.setPassword("12345612");
        if (user == null) {
            return null;
        } else if(user.getStatus().equals("0")){
            return null;
        }else{
            //创建权限集合
            List<GrantedAuthority> grantedList = new ArrayList<>();
            //向权限集合中添加权限
            System.out.println("授权成功");
            grantedList.add(new SimpleGrantedAuthority("ROLE_USER"));
            return new User(username,user.getPassword(),grantedList);
        }
    }

}
