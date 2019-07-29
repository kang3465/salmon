package cn.ele.core.service.category;

import cn.ele.core.entity.PageResult;
import cn.ele.core.pojo.category.Specification;
import cn.ele.core.pojo.category.SpecificationEntity;

import java.util.List;

/**
 * @author kang
 */
public interface SpecificationService {
    /**
     * 查询所有的规格
     * @return
     * @throws Exception
     */
    List<SpecificationEntity> querySpecificationAll() throws Exception;
    /**
     * 根据ID查询规格
     * @param id
     * @return
     * @throws Exception
     */
    SpecificationEntity queryOne(Long id) throws Exception;

    /**
     * 查询出所有规格并分页
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
    int addSpecification(SpecificationEntity specification)throws Exception;
    /**
     * 保存现有的规格信息
     * @param specification 保存的规格对象
     * @return
     * @throws Exception
     */
    int saveSpecification(SpecificationEntity specification)throws Exception;
    /**
     * 根据ID删除规格信息
     * @param id 保存的规格对象
     * @return
     * @throws Exception
     */
    int deleteSpecificationByID(Long id)throws Exception;

    /**
     * 根据Ids删除多个规格
     * @param ids
     * @return
     */
    int deleteSpecificationByIDs(String ids);

    /**
     * 不包括详细的选项
     * @return
     */
    List<Specification> querySpecificationAllSimple();
}
