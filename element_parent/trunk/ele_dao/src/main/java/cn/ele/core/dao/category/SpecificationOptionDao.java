package cn.ele.core.dao.category;

import cn.ele.core.pojo.category.SpecificationOption;
import cn.ele.core.pojo.category.SpecificationOptionQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SpecificationOptionDao {
    int countByExample(SpecificationOptionQuery example);

    int deleteByExample(SpecificationOptionQuery example);

    int insert(SpecificationOption record);

    int insertSelective(SpecificationOption record);

    List<SpecificationOption> selectByExample(SpecificationOptionQuery example);

    int updateByExampleSelective(@Param("record") SpecificationOption record, @Param("example") SpecificationOptionQuery example);

    int updateByExample(@Param("record") SpecificationOption record, @Param("example") SpecificationOptionQuery example);
}