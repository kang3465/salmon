package cn.ele.core.dao.areas;

import cn.ele.core.pojo.areas.Cities;
import cn.ele.core.pojo.areas.CitiesQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CitiesDao {
    int countByExample(CitiesQuery example);

    int deleteByExample(CitiesQuery example);

    int insert(Cities record);

    int insertSelective(Cities record);

    List<Cities> selectByExample(CitiesQuery example);

    int updateByExampleSelective(@Param("record") Cities record, @Param("example") CitiesQuery example);

    int updateByExample(@Param("record") Cities record, @Param("example") CitiesQuery example);
}