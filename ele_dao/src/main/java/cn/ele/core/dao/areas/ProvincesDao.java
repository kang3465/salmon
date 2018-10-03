package cn.ele.core.dao.areas;

import cn.ele.core.pojo.areas.Provinces;
import cn.ele.core.pojo.areas.ProvincesQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProvincesDao {
    int countByExample(ProvincesQuery example);

    int deleteByExample(ProvincesQuery example);

    int insert(Provinces record);

    int insertSelective(Provinces record);

    List<Provinces> selectByExample(ProvincesQuery example);

    int updateByExampleSelective(@Param("record") Provinces record, @Param("example") ProvincesQuery example);

    int updateByExample(@Param("record") Provinces record, @Param("example") ProvincesQuery example);
}