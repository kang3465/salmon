package cn.ele.core.service.category;

import cn.ele.core.entity.PageResult;
import cn.ele.core.pojo.category.MaterialTypeTemplate;

import java.util.List;

public interface MaterialTypeTemplateSrevice {
    /**
     * 查询所有模板
     * @return
     * @throws Exception
     */
    List<MaterialTypeTemplate> queryAll() throws Exception;

    /**
     * 分页查询所有模板
     * @param pageNum 第几页
     * @param pageSize 每页几条数据
     * @return PageResult 分页实体
     * @throws Exception
     */
    PageResult queryAllByPage(Integer pageNum, Integer pageSize) throws Exception;

    /**
     * 根据id查询模板信息
     * @param id 模板ID
     * @return 查询到的模板对象，未查找到返回null
     * @throws Exception
     */
    MaterialTypeTemplate queryByID(Long id) throws Exception;

    /**
     * 新增一条素材模板数据
     * @param materialTypeTemplate
     * @return
     * @throws Exception
     */
    int add(MaterialTypeTemplate materialTypeTemplate) throws Exception;

    /**
     * 更新一条素材模板数据
     * @param materialTypeTemplate
     * @return 跟新的条数
     * @throws Exception
     */
    int update(MaterialTypeTemplate materialTypeTemplate) throws Exception;

    /**
     * 根据id删除一条数据
     * @param id
     * @return
     * @throws Exception
     */
    int deleteByID(Integer id) throws Exception;

}
