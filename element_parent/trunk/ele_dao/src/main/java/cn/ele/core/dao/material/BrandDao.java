package cn.ele.core.dao.material;

import cn.ele.core.pojo.material.Brand;
import cn.ele.core.pojo.material.BrandQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BrandDao {
    int countByExample(BrandQuery example);

    int deleteByExample(BrandQuery example);

    int insert(Brand record);

    int insertSelective(Brand record);

    List<Brand> selectByExample(BrandQuery example);

    int updateByExampleSelective(@Param("record") Brand record, @Param("example") BrandQuery example);

    int updateByExample(@Param("record") Brand record, @Param("example") BrandQuery example);
}