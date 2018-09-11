package cn.ele.core.service.category;

import cn.ele.core.dao.category.CategoryDao;
import cn.ele.core.pojo.category.Category;
import cn.ele.core.pojo.category.CategoryQuery;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author kang
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    CategoryDao categoryDao;
    /**
     * 根据ID查找父集目录
     * @return
     * @throws Exception
     */

    @Override
    public List<Category> queryParentCategory() throws Exception {
        return null;
    }

    /**
     * 根据父级目录ID查找分类
     * @param  parentID 父级目录的ID
     * @return
     * @throws Exception
     */
    @Override
    public List<Category> queryCategoryByParentID(Long parentID) throws Exception{

        CategoryQuery categoryQuery = new CategoryQuery();
        categoryQuery.createCriteria().andParentIdEqualTo(parentID);

        return categoryDao.selectByExample(categoryQuery);
    }

    /**
     * 添加分类
     * @param category 需要添加的分类实体类型（没有主键）
     * @return
     * @throws Exception
     */
    @Override
    public int addCategory(Category category) throws Exception{return 0;}

    /**
     * 整组添加分类信息，包括子集分类信息
     * @param category
     * @return
     * @throws Exception
     */
    @Override
    public int addCategoryTogether(Category category) throws Exception{return 0;}

    /**
     * 保存更新单条分类信息
     * @param category
     * @return
     * @throws Exception
     */
    @Override
    public int saveCategory(Category category) throws Exception{return 0;}

    @Override
    public int deleteCategoryByID(Integer id) throws Exception {
        return 0;
    }

}
