package cn.ele.core.service.user;

import cn.ele.core.dao.user.*;
import cn.ele.core.pojo.entity.PageResult;
import cn.ele.core.pojo.user.*;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

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

    @Override
    public User findOneByUserName(String userName) throws Exception {
        UserQuery userQuery = new UserQuery();
        UserQuery.Criteria criteria = userQuery.createCriteria();
        criteria.andUsernameEqualTo(userName);
        userQuery.or(criteria);
        List<User> users = userDao.selectByExample(userQuery);
        if (users.size() <= 0) return null;
        else if (users.size()>1) throw new Exception("请联系数据库管理员：数据出错，用户数据重复需要管理员维护数据");
        return users.get(0);
    }

    @Override
    public List<Role> findRolesByUserId(Long userId) {
        UserRoleLinkQuery userRoleLinkQuery = new UserRoleLinkQuery();
        UserRoleLinkQuery.Criteria userRoleLinkQueryCriteria = userRoleLinkQuery.createCriteria();
        userRoleLinkQueryCriteria.andUserIdEqualTo(Math.toIntExact(userId));
        userRoleLinkQuery.or(userRoleLinkQueryCriteria);
        List<UserRoleLinkKey> userRoleLinkKeys = userRoleLinkDao.selectByExample(userRoleLinkQuery);

        RoleQuery roleQuery = new RoleQuery();
        RoleQuery.Criteria roleQueryCriteria = roleQuery.createCriteria();

        for (UserRoleLinkKey userRoleLinkKey : userRoleLinkKeys) {
            roleQueryCriteria.andIdEqualTo(userRoleLinkKey.getRoleId());
            roleQuery.or(roleQueryCriteria);
        }
        return roleDao.selectByExample(roleQuery);
    }

    @Override
    public Role findRoleByUserId(Long userId) {
        UserRoleLinkQuery userRoleLinkQuery = new UserRoleLinkQuery();
        UserRoleLinkQuery.Criteria userRoleLinkQueryCriteria = userRoleLinkQuery.createCriteria();
        userRoleLinkQueryCriteria.andUserIdEqualTo(Math.toIntExact(userId));
        userRoleLinkQuery.or(userRoleLinkQueryCriteria);
        List<UserRoleLinkKey> userRoleLinkKeys = userRoleLinkDao.selectByExample(userRoleLinkQuery);
        return roleDao.selectByPrimaryKey(userRoleLinkKeys.get(0).getRoleId());
    }

    @Override
    public List<Permission> findPermissionByUserId(Long userId) {
        Role roleByUserId = findRoleByUserId(userId);
        RolePermissionLinkQuery rolePermissionLinkQuery = new RolePermissionLinkQuery();
        RolePermissionLinkQuery.Criteria rolePermissionLinkQueryCriteria = rolePermissionLinkQuery.createCriteria();
        rolePermissionLinkQueryCriteria.andRoleIdEqualTo(roleByUserId.getId());
        rolePermissionLinkQuery.or(rolePermissionLinkQueryCriteria);
        List<RolePermissionLink> rolePermissionLinks = rolePermissionLinkDao.selectByExample(rolePermissionLinkQuery);

        PermissionQuery permissionQuery = new PermissionQuery();
        PermissionQuery.Criteria permissionQueryCriteria = permissionQuery.createCriteria();

        for (RolePermissionLink rolePermissionLink : rolePermissionLinks) {
            permissionQueryCriteria.andIdEqualTo(rolePermissionLink.getRoleId());
            permissionQuery.or(permissionQueryCriteria);
        }
        return permissionDao.selectByExample(permissionQuery);
    }

    @Override
    public int saveUser(User user) {
        UserQuery userQuery = new UserQuery();
        UserQuery.Criteria userQueryCriteria = userQuery.createCriteria();
        userQueryCriteria.andIdEqualTo(user.getId());
        return userDao.updateByExample(user, userQuery);
    }

    @Override
    public int addUser(User user) throws Exception {
        user.setUpdated(new Timestamp(new Date().getTime()));
        user.setBirthday(new Timestamp(new Date().getTime()));
        user.setLastLoginTime(new Timestamp(new Date().getTime()));
        userDao.insertSelective(user);
        String username = user.getUsername();
        user = findOneByUserName(username);
        UserRoleLinkKey userRoleLinkKey = new UserRoleLinkKey();
        userRoleLinkKey.setRoleId(1);
        userRoleLinkKey.setUserId(Math.toIntExact(user.getId()));
        return userRoleLinkDao.insertSelective(userRoleLinkKey);
    }

    @Override
    public User findOneByEmail(String email) {
        UserQuery userQuery = new UserQuery();
        UserQuery.Criteria criteria = userQuery.createCriteria();
        criteria.andEmailEqualTo(email);
        List<User> users = userDao.selectByExample(userQuery);
        return users.get(0);
    }

    @Override
    public PageResult queryAllUserListIncludeAllMessageByPage( Integer pageNum, Integer pageSize) {
        /*调用mybatis的分页插件*/
        PageHelper.startPage(pageNum, pageSize);
        Page<User> page = (Page<User>) userDao.selectByExample(null);
        /*自己创建一个实例存放分页的结果并返回*/
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public PageResult queryAllUserListSafeByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<User> page = (Page<User>) userDao.selectByExample(null);
        /*自己创建一个实例存放分页的结果并返回*/
        return new PageResult(page.getTotal(), page.getResult());
    }

}
