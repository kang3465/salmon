package cn.ele.core.service.user;

import cn.ele.core.pojo.user.User;

public interface UserService {
    public User findOneByUserName(String userName) throws Exception;
}
