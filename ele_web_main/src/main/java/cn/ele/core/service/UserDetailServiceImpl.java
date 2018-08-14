package cn.ele.core.service;

import cn.ele.core.pojo.user.Permission;
import cn.ele.core.service.user.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDetailServiceImpl implements UserDetailsService {

    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        cn.ele.core.pojo.user.User user = null;
        username = username.trim();
        try {
            user = userService.findOneByUserName(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user == null) {
            return null;
        } else if (user.getStatus().equals("0")) {
            throw new UsernameNotFoundException("用户处于非正常状态");
        } else {
            //创建权限集合
            List<GrantedAuthority> grantedList = new ArrayList<>();
            //向权限集合中添加权限
            List<Permission> permissionByUserId = null;
            try {
                permissionByUserId = userService.findPermissionByUserId(user.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (permissionByUserId != null) {
                for (Permission aPermissionByUserId : permissionByUserId) {
                    System.out.println(aPermissionByUserId.getName());
                    grantedList.add(new SimpleGrantedAuthority(aPermissionByUserId.getName()));
                }
            }
//            System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
            //更新用户登录时间
            try {
                user.setLastLoginTime(new Date());
                userService.saveUser(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new User(username, user.getPassword(), grantedList);
        }
    }

}
