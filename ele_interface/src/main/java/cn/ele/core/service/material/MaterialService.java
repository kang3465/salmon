package cn.ele.core.service.material;

import cn.ele.core.entity.PageResult;
import cn.ele.core.pojo.material.MaterialEntity;

/**
 * @author kang
 */
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
     * @param materialEntity 要更新的素材（ID不为空）
     * @return
     * @throws Exception
     */
    int update(MaterialEntity materialEntity) throws Exception;

    /**
     * 根据ID删除一条素材信息(逻辑删除)
     * @param id 需要删除的素材ID
     * @return 返回删除的数量
     * @throws Exception
     */
    int deleteByID(Integer id) throws Exception;

    /**
     * 根据IDS字符串删除素材 （英文逗号隔开）
     * @param ids  英文逗号隔开
     * @return 返回删除的数量
     * @throws Exception
     */
    int deleteByIDs(String ids) throws Exception;

    /**
     * 根据ID查询商品详情
     * @param id 素材ID
     * @return 返回MaterialEntity对象（关于素材的所有信息）
     * @throws Exception
     */
    MaterialEntity queryOneByID(Integer id) throws Exception;

    /**
     * 分页查询素材
     * @param pageNum 第几页
     * @param pageSize 每页显示几条数据
     * @return PageResult分页结果
     * @throws Exception
     */

    PageResult queryAllByPage(Integer pageNum, Integer pageSize) throws Exception;
}
