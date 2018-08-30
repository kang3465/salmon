package cn.ele.core.service.category;

import cn.ele.core.pojo.category.Specification;
import cn.ele.core.pojo.entity.PageResult;

import java.util.List;

/**
 * @author kang
 */
public interface SpecificationService {
    /**
     * 查询所有的分类
     * @return
     * @throws Exception
     */
    List<Specification> querySpecificationAll() throws Exception;

    /**
     * 查询出所有分类并分页
     * @param pageNum 第几页
     * @param pageSize 每页的数量
     * @return
     * @throws Exception
     */
    PageResult querySpecificationAllByPage(Integer pageNum, Integer pageSize) throws Exception;

    /**
     * 根据规格ID查询规格
     * @param specificationID
     * @return
     * @throws Exception
     */
    Specification querySpecificationByID( Integer specificationID) throws Exception;

    /**
     * 添加新的规格信息
     * @param specification 添加的规格对象
     * @return
     * @throws Exception
     */
    int addSpecification(Specification specification)throws Exception;
    /**
     * 保存现有的规格信息
     * @param specification 保存的规格对象
     * @return
     * @throws Exception
     */
    int saveSpecification(Specification specification)throws Exception;
    /**
     * 根据ID删除规格信息
     * @param specificationID 保存的规格对象
     * @return
     * @throws Exception
     */
    int deleteSpecificationByID(Integer specificationID)throws Exception;

}
