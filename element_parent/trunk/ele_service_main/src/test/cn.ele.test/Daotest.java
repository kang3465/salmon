package cn.ele.test;

import cn.ele.core.dao.user.UserDao;
import cn.ele.core.pojo.user.UserQuery;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * @package :cn.ele.test
 *  * @Author        :fjnet
 * @Date :Create in 2018年 09月 30日  15:39 2018/9/30
 * @Description : ${description}
 * @Modified By :
 * @Version :&Version&
 **/
@ContextConfiguration(locations = {"classpath:spring/application*.xml"})
public class Daotest extends AbstractJUnit4SpringContextTests {
    @Autowired
    public UserDao userDao;
    @Test
    public void demo(){
        UserQuery userQuery = new UserQuery();
        userQuery.createCriteria().andIdEqualTo(1L);
//        List<User> users = userDao.selectUserAndRolesByExample(userQuery);
//        System.out.println(users.get(0));
    }
}
