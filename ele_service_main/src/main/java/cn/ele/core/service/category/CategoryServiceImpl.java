package cn.ele.core.service.category;

import cn.ele.core.dao.category.CategoryDao;
import cn.ele.core.entity.PageResult;
import cn.ele.core.pojo.category.Category;
import cn.ele.core.pojo.category.CategoryQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Override
    public int deleteCategoryByIDs(String ids, String reg) throws Exception {
        String[] split = ids.split(",");
        CategoryQuery categoryQuery = new CategoryQuery();
        List<Long> idList = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            idList.add(Long.valueOf(split[i]));
        }
        categoryQuery.createCriteria().andIdIn(idList);
        return  categoryDao.deleteByExample(categoryQuery);
    }

    @Override
    public int deleteCategoryByIDs(String ids) throws Exception {
        return deleteCategoryByIDs(ids,",");
    }

    @Override
    public PageResult queryCategoryByParentID(Category entity, Integer pageNum, Integer pageSize, String keywords) {
        PageHelper.startPage(pageNum,pageSize);
        CategoryQuery categoryQuery = new CategoryQuery();
        categoryQuery.createCriteria().andParentIdEqualTo(entity.getParentId());
        Page<Category> page = (Page<Category>) categoryDao.selectByExample(categoryQuery);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public Category findOneByID(Long id) {
        CategoryQuery categoryQuery = new CategoryQuery();
        categoryQuery.createCriteria().andIdEqualTo(id);
        return categoryDao.selectByExample(categoryQuery).get(0);
    }

}
