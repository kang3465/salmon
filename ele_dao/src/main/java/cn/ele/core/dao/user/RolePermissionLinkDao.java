package cn.ele.core.dao.user;

import cn.ele.core.pojo.user.RolePermissionLink;
import cn.ele.core.pojo.user.RolePermissionLinkKey;
import cn.ele.core.pojo.user.RolePermissionLinkQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RolePermissionLinkDao {
    int countByExample(RolePermissionLinkQuery example);

    int deleteByExample(RolePermissionLinkQuery example);

    int deleteByPrimaryKey(RolePermissionLinkKey key);

    int insert(RolePermissionLink record);

    int insertSelective(RolePermissionLink record);

    List<RolePermissionLink> selectByExample(RolePermissionLinkQuery example);

    RolePermissionLink selectByPrimaryKey(RolePermissionLinkKey key);

    int updateByExampleSelective(@Param("record") RolePermissionLink record, @Param("example") RolePermissionLinkQuery example);

    int updateByExample(@Param("record") RolePermissionLink record, @Param("example") RolePermissionLinkQuery example);

    int updateByPrimaryKeySelective(RolePermissionLink record);

    int updateByPrimaryKey(RolePermissionLink record);
}