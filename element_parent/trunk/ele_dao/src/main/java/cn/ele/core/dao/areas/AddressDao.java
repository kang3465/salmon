package cn.ele.core.dao.areas;

import cn.ele.core.pojo.areas.Address;
import cn.ele.core.pojo.areas.AddressQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AddressDao {
    int countByExample(AddressQuery example);

    int deleteByExample(AddressQuery example);

    int insert(Address record);

    int insertSelective(Address record);

    List<Address> selectByExample(AddressQuery example);

    int updateByExampleSelective(@Param("record") Address record, @Param("example") AddressQuery example);

    int updateByExample(@Param("record") Address record, @Param("example") AddressQuery example);
}