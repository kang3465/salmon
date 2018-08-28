package cn.ele.core.dao.user;

import cn.ele.core.pojo.user.UserRoleLinkKey;
import cn.ele.core.pojo.user.UserRoleLinkQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserRoleLinkDao {
    int countByExample(UserRoleLinkQuery example);

    int deleteByExample(UserRoleLinkQuery example);

    int deleteByPrimaryKey(UserRoleLinkKey key);

    int insert(UserRoleLinkKey record);

    int insertSelective(UserRoleLinkKey record);

    List<UserRoleLinkKey> selectByExample(UserRoleLinkQuery example);

    int updateByExampleSelective(@Param("record") UserRoleLinkKey record, @Param("example") UserRoleLinkQuery example);

    int updateByExample(@Param("record") UserRoleLinkKey record, @Param("example") UserRoleLinkQuery example);
}