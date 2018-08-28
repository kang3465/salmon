package cn.ele.core.dao.category;

import cn.ele.core.pojo.category.Specification;
import cn.ele.core.pojo.category.SpecificationQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SpecificationDao {
    int countByExample(SpecificationQuery example);

    int deleteByExample(SpecificationQuery example);

    int insert(Specification record);

    int insertSelective(Specification record);

    List<Specification> selectByExample(SpecificationQuery example);

    int updateByExampleSelective(@Param("record") Specification record, @Param("example") SpecificationQuery example);

    int updateByExample(@Param("record") Specification record, @Param("example") SpecificationQuery example);
}