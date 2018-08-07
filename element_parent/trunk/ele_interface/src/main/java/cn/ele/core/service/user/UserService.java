package cn.ele.core.service.user;

import cn.ele.core.pojo.user.Permission;
import cn.ele.core.pojo.user.Role;
import cn.ele.core.pojo.user.User;

import java.util.List;

public interface UserService {
    /**
     * 通过用户名查找单个用户
     * @param userName
     * @return
     * @throws Exception
     */
    public User findOneByUserName(String userName) throws Exception;

    /**
     * 通过UserID查找用户的角色集合
     * @param userId
     * @return
     * @throws Exception
     */
    public List<Role> findRolesByUserId(Long userId) throws Exception;

    /**
     *  通过 UserID 查找用户的角色
     * @param userId
     * @return
     * @throws Exception
     */
    public Role findRoleByUserId(Long userId) throws Exception;

    /**
     * 通过 UserID 查找用户所拥有的权限集合
     * @param userId
     * @return
     * @throws Exception
     */
    public List<Permission> findPermissionByUserId(Long userId) throws Exception;
    /**
     * 根据 UserID 更新用户信息
     * @param user
     * @return
     * @throws Exception
     */
    public int saveUser(User user) throws Exception;

    /**
     * 添加用户 User
     * @param user
     * @return
     * @throws Exception
     */
    public int addUser(User user) throws Exception;

    /**
     * 根据Email查找单个用户
     * @param email
     * @return
     */
    User findOneByEmail(String email);
}
