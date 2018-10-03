package cn.ele.core.dao.material;

import cn.ele.core.pojo.material.MaterialItem;
import cn.ele.core.pojo.material.MaterialItemQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MaterialItemDao {
    int countByExample(MaterialItemQuery example);

    int deleteByExample(MaterialItemQuery example);

    int insert(MaterialItem record);

    int insertSelective(MaterialItem record);

    List<MaterialItem> selectByExample(MaterialItemQuery example);

    int updateByExampleSelective(@Param("record") MaterialItem record, @Param("example") MaterialItemQuery example);

    int updateByExample(@Param("record") MaterialItem record, @Param("example") MaterialItemQuery example);
}