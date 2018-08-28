package cn.ele.core.service.category;

import cn.ele.core.pojo.category.Category;

import java.util.List;

public interface CategoryService {
    /**
     * 查找一级目录
     * @return
     * @throws Exception
     */
    List<Category> queryParentCategory() throws Exception;

    /**
     * 根据父级目录ID查找分类
     * @param  parentID 父级目录的ID
     * @return
     * @throws Exception
     */
    List<Category> queryCategoryByParentID(Integer parentID) throws Exception;

    /**
     * 添加分类
     * @param category 需要添加的分类实体类型（没有主键）
     * @return
     * @throws Exception
     */
    int addCategory(Category category) throws Exception;

    /**
     * 整组添加分类信息，包括子集分类信息
     * @param category
     * @return
     * @throws Exception
     */
    int addCategoryTogether(Category category) throws Exception;

    /**
     * 保存更新单条分类信息
     * @param category
     * @return
     * @throws Exception
     */
    int saveCategory(Category category) throws Exception;

}
