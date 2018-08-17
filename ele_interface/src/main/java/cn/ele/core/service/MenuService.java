package cn.ele.core.service;

import cn.ele.core.pojo.user.Menu;
import cn.ele.core.pojo.user.User;

import java.util.List;

public interface MenuService {
    List<Menu> queryMenuByUser(User user) throws Exception;
}
