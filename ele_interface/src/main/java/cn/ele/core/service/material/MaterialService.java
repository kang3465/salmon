package cn.ele.core.service.material;

import cn.ele.core.pojo.entity.PageResult;
import cn.ele.core.pojo.material.MaterialEntity;

public interface MaterialService {
    /**
     * 新增一条素材
     * @param materialEntity
     * @return
     * @throws Exception
     */
    int add(MaterialEntity materialEntity) throws Exception;

    /**
     * 更新一条素材（ID不为空）
     * @param materialEntity
     * @return
     * @throws Exception
     */
    int update(MaterialEntity materialEntity) throws Exception;

    /**
     * 根据ID删除一条素材信息(逻辑删除)
     * @param id
     * @return
     * @throws Exception
     */
    int deleteByID(Integer id) throws Exception;

    /**
     * 根据ID查询商品详情
     * @param id
     * @return
     * @throws Exception
     */
    MaterialEntity queryOneByID(Integer id) throws Exception;

    /**
     * 分页查询素材
     * @param pageNum
     * @param pageSize
     * @return PageResult分页结果
     * @throws Exception
     */

    PageResult queryAllByPage(Integer pageNum, Integer pageSize) throws Exception;
}
