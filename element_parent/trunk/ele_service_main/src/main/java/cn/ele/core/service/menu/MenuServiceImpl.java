package cn.ele.core.service.menu;

import cn.ele.core.dao.user.*;
import cn.ele.core.pojo.user.*;
import cn.ele.core.service.MenuService;
import cn.ele.core.service.user.UserService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    UserDao userDao;
    @Autowired
    UserRoleLinkDao userRoleLinkDao;
    @Autowired
    RoleDao roleDao;
    @Autowired
    RolePermissionLinkDao rolePermissionLinkDao;
    @Autowired
    PermissionDao permissionDao;
    @Autowired
    MenuDao menuDao;
    @Autowired
    RoleMenuLinkDao roleMenuLinkDao;
    @Autowired
    UserService userService;


    @Override
    public List<Menu> queryMenuByUser(User user) throws Exception {
        /*创建要返回的结果集*/
        List<Map<String,Object>> resultList = new ArrayList<>();
        //根据userID获得用户的角色，目前认为一个用户只能拥有一个角色
        Role role = userService.findRoleByUserId(user.getId());
        RoleMenuLinkQuery roleMenuLinkQuery = new RoleMenuLinkQuery();
        RoleMenuLinkQuery.Criteria roleMenuLinkQueryCriteria = roleMenuLinkQuery.createCriteria();
        roleMenuLinkQueryCriteria.andRoleIdEqualTo(role.getId());
        List<RoleMenuLink> roleMenuLinks = roleMenuLinkDao.selectByExample(roleMenuLinkQuery);
        /*创建存放第一级Menu的ID集合对象*/
        List<Menu> menuList = queryMenuByPid((long) 0);

        /*循环第一级菜单I的集合，获取第二级菜单目录*/
        for(Menu menu:menuList){
            menu.setMenuList(queryMenuByPid(menu.getId()));
        }
        return menuList;
    }

    @Override
    public List<Menu> queryMenuByPid(Long pid) throws Exception {
        MenuQuery menuQuery = new MenuQuery();
        MenuQuery.Criteria criteria = menuQuery.createCriteria().andPidEqualTo(pid);menuQuery.setOrderByClause("priority");
        return menuDao.selectByExample(menuQuery);
    }

}
