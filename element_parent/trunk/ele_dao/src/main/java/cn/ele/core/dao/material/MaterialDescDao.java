package cn.ele.core.dao.material;

import cn.ele.core.pojo.material.MaterialDesc;
import cn.ele.core.pojo.material.MaterialDescQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MaterialDescDao {
    int countByExample(MaterialDescQuery example);

    int deleteByExample(MaterialDescQuery example);

    int insert(MaterialDesc record);

    int insertSelective(MaterialDesc record);

    List<MaterialDesc> selectByExample(MaterialDescQuery example);

    int updateByExampleSelective(@Param("record") MaterialDesc record, @Param("example") MaterialDescQuery example);

    int updateByExample(@Param("record") MaterialDesc record, @Param("example") MaterialDescQuery example);
}