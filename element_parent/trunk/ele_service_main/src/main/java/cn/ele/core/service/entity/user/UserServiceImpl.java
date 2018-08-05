package cn.ele.core.service.entity.user;

import cn.ele.core.dao.user.UserDao;
import cn.ele.core.pojo.user.User;
import cn.ele.core.pojo.user.UserQuery;
import cn.ele.core.service.user.UserService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public User findOneByUserName(String userName) throws Exception {
        UserQuery userQuery = new UserQuery();
        UserQuery.Criteria criteria = userQuery.createCriteria();
        criteria.andUsernameEqualTo(userName);
        userQuery.or(criteria);
        List<User> users = userDao.selectByExample(userQuery);
        if (users.size()<=0||users==null){
            return null;
        }else if (users.size()>1){
            throw new Exception("数据出错");
        }
        return users.get(0);
    }
}
