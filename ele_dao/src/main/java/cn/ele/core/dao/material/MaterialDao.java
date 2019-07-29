package cn.ele.core.dao.material;

import cn.ele.core.pojo.material.Material;
import cn.ele.core.pojo.material.MaterialEntity;
import cn.ele.core.pojo.material.MaterialQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MaterialDao {
    int countByExample(MaterialQuery example);

    int deleteByExample(MaterialQuery example);

    int insert(Material record);

    int insertSelective(Material record);

    List<Material> selectByExample(MaterialQuery example);

    List<MaterialEntity> selectDescByExample(Material example);

    int updateByExampleSelective(@Param("record") Material record, @Param("example") MaterialQuery example);

    int updateByExample(@Param("record") Material record, @Param("example") MaterialQuery example);
}