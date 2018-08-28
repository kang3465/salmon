package cn.ele.core.dao.user;

import cn.ele.core.pojo.user.RoleMenuLink;
import cn.ele.core.pojo.user.RoleMenuLinkKey;
import cn.ele.core.pojo.user.RoleMenuLinkQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleMenuLinkDao {
    int countByExample(RoleMenuLinkQuery example);

    int deleteByExample(RoleMenuLinkQuery example);

    int deleteByPrimaryKey(RoleMenuLinkKey key);

    int insert(RoleMenuLink record);

    int insertSelective(RoleMenuLink record);

    List<RoleMenuLink> selectByExample(RoleMenuLinkQuery example);

    RoleMenuLink selectByPrimaryKey(RoleMenuLinkKey key);

    int updateByExampleSelective(@Param("record") RoleMenuLink record, @Param("example") RoleMenuLinkQuery example);

    int updateByExample(@Param("record") RoleMenuLink record, @Param("example") RoleMenuLinkQuery example);

    int updateByPrimaryKeySelective(RoleMenuLink record);

    int updateByPrimaryKey(RoleMenuLink record);
}