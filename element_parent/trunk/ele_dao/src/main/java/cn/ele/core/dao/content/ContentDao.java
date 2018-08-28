package cn.ele.core.dao.content;

import cn.ele.core.pojo.content.Content;
import cn.ele.core.pojo.content.ContentQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ContentDao {
    int countByExample(ContentQuery example);

    int deleteByExample(ContentQuery example);

    int insert(Content record);

    int insertSelective(Content record);

    List<Content> selectByExample(ContentQuery example);

    int updateByExampleSelective(@Param("record") Content record, @Param("example") ContentQuery example);

    int updateByExample(@Param("record") Content record, @Param("example") ContentQuery example);
}