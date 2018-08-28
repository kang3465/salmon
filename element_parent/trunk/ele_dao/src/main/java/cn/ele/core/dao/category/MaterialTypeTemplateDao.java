package cn.ele.core.dao.category;

import cn.ele.core.pojo.category.MaterialTypeTemplate;
import cn.ele.core.pojo.category.MaterialTypeTemplateQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MaterialTypeTemplateDao {
    int countByExample(MaterialTypeTemplateQuery example);

    int deleteByExample(MaterialTypeTemplateQuery example);

    int insert(MaterialTypeTemplate record);

    int insertSelective(MaterialTypeTemplate record);

    List<MaterialTypeTemplate> selectByExample(MaterialTypeTemplateQuery example);

    int updateByExampleSelective(@Param("record") MaterialTypeTemplate record, @Param("example") MaterialTypeTemplateQuery example);

    int updateByExample(@Param("record") MaterialTypeTemplate record, @Param("example") MaterialTypeTemplateQuery example);
}