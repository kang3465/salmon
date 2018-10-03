package cn.ele.core.dao.areas;

import cn.ele.core.pojo.areas.Areas;
import cn.ele.core.pojo.areas.AreasQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AreasDao {
    int countByExample(AreasQuery example);

    int deleteByExample(AreasQuery example);

    int insert(Areas record);

    int insertSelective(Areas record);

    List<Areas> selectByExample(AreasQuery example);

    int updateByExampleSelective(@Param("record") Areas record, @Param("example") AreasQuery example);

    int updateByExample(@Param("record") Areas record, @Param("example") AreasQuery example);
}