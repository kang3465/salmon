package cn.ele.core.dao.content;

import cn.ele.core.pojo.content.ContentCategory;
import cn.ele.core.pojo.content.ContentCategoryQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ContentCategoryDao {
    int countByExample(ContentCategoryQuery example);

    int deleteByExample(ContentCategoryQuery example);

    int insert(ContentCategory record);

    int insertSelective(ContentCategory record);

    List<ContentCategory> selectByExample(ContentCategoryQuery example);

    int updateByExampleSelective(@Param("record") ContentCategory record, @Param("example") ContentCategoryQuery example);

    int updateByExample(@Param("record") ContentCategory record, @Param("example") ContentCategoryQuery example);
}