package cn.ele.core.dao.category;

import cn.ele.core.pojo.category.Category;
import cn.ele.core.pojo.category.CategoryQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryDao {
    int countByExample(CategoryQuery example);

    int deleteByExample(CategoryQuery example);

    int insert(Category record);

    int insertSelective(Category record);

    List<Category> selectByExample(CategoryQuery example);

    int updateByExampleSelective(@Param("record") Category record, @Param("example") CategoryQuery example);

    int updateByExample(@Param("record") Category record, @Param("example") CategoryQuery example);
}