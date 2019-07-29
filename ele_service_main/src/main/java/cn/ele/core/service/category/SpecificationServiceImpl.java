package cn.ele.core.service.category;

import cn.ele.core.dao.category.SpecificationDao;
import cn.ele.core.dao.category.SpecificationOptionDao;
import cn.ele.core.entity.PageResult;
import cn.ele.core.pojo.category.*;
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
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    SpecificationDao specificationDao;
    @Autowired
    SpecificationOptionDao specificationOptionDao;
    /**
     * 查询所有的分类
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<SpecificationEntity> querySpecificationAll() throws Exception {
        List<Specification> specifications = specificationDao.selectByExample(null);
        List<SpecificationEntity> specificationEntities = new ArrayList<>();
        for (int i = 0; i < specifications.size(); i++) {
            SpecificationEntity specificationEntity = new SpecificationEntity();
            specificationEntity.setSpecification(specifications.get(i));
            SpecificationOptionQuery specificationOptionQuery = new SpecificationOptionQuery();
            specificationOptionQuery.createCriteria().andIdEqualTo(specifications.get(i).getId());
            List<SpecificationOption> specificationOptions = specificationOptionDao.selectByExample(specificationOptionQuery);
            specificationEntity.setSpecificationOptionList(specificationOptions);
            specificationEntities.add(specificationEntity);

        }
        return specificationEntities;
    }

    @Override
    public SpecificationEntity queryOne(Long id) throws Exception {
        /*创建封装的规格实体*/
        SpecificationEntity entity = new SpecificationEntity();
        /*根据规格ID查询规格*/
        SpecificationQuery specificationQuery = new SpecificationQuery();
        specificationQuery.createCriteria().andIdEqualTo(id);
        /*将规格封装到规格Entity中*/
        entity.setSpecification(specificationDao.selectByExample(specificationQuery).get(0));
        /*根据规格ID查询规格选项*/
        SpecificationOptionQuery specificationOptionQuery = new SpecificationOptionQuery();
        specificationOptionQuery.createCriteria().andSpecIdEqualTo(id);
        /*将规格选型封装到规格Entity中*/
        entity.setSpecificationOptionList(specificationOptionDao.selectByExample(specificationOptionQuery));
        return entity;
    }

    /**
     * 查询出所有分类并分页
     *
     * @param pageNum  第几页
     * @param pageSize 每页的数量
     * @return
     * @throws Exception
     */
    @Override
    public PageResult querySpecificationAllByPage(Integer pageNum, Integer pageSize) throws Exception {
        /*启用mybatis分页插件*/
        PageHelper.startPage(pageNum, pageSize);
        /*获取分页结果*/
        Page<Specification> page = (Page<Specification>) specificationDao.selectByExample(null);
        /*自己创建一个实例存放分页的结果并返回*/
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据规格ID查询规格
     * @param specificationID
     * @return
     * @throws Exception
     */
    @Override
    public Specification querySpecificationByID(Integer specificationID) throws Exception {
        return null;
    }

    /**
     * 添加新的规格信息
     *
     * @param entity 添加的规格对象
     * @return
     * @throws Exception
     */
    @Override
    public int addSpecification(SpecificationEntity entity) throws Exception {
        Specification specification = entity.getSpecification();
        int i1 = specificationDao.insertSelective(specification);
        List<SpecificationOption> specificationOptionList = entity.getSpecificationOptionList();
        for (int i = 0; i <specificationOptionList.size() ; i++) {
            SpecificationOption specificationOption = specificationOptionList.get(i);
            specificationOption.setSpecId(specification.getId());
            specificationOptionDao.insertSelective(specificationOption);
        }
        return i1;
    }

    /**
     * 保存现有的规格信息
     *
     * @param entity 保存的规格对象
     * @return
     * @throws Exception
     */
    @Override
    public int saveSpecification(SpecificationEntity entity) throws Exception {
        /*更新规格*/
        SpecificationQuery specificationQuery = new SpecificationQuery();
        specificationQuery.createCriteria().andIdEqualTo(entity.getSpecification().getId());
        int i1 = specificationDao.updateByExampleSelective(entity.getSpecification(), specificationQuery);
        /*删除原有的规格选项*/
        SpecificationOptionQuery specificationOptionQuery = new SpecificationOptionQuery();
        specificationOptionQuery.createCriteria().andSpecIdEqualTo(entity.getSpecification().getId());
        specificationOptionDao.deleteByExample(specificationOptionQuery);
        /*将新的规格选项添加*/
        List<SpecificationOption> specificationOptionList = entity.getSpecificationOptionList();
        for (int i = 0; i <specificationOptionList.size() ; i++) {
            SpecificationOption specificationOption = specificationOptionList.get(i);
            specificationOption.setSpecId(entity.getSpecification().getId());
            specificationOptionDao.insertSelective(specificationOption);
        }

        return i1;
    }

    /**
     * 根据ID删除规格信息
     *
     * @param id 删除的规格对象
     * @return
     * @throws Exception
     */
    @Override
    public int deleteSpecificationByID(Long id) throws Exception {
        /*创建规格的删除条件*/
        SpecificationQuery specificationQuery = new SpecificationQuery();
        specificationQuery.createCriteria().andIdEqualTo(id);
        /*创建该规格下的规格选项的删除条件*/
        SpecificationOptionQuery specificationOptionQuery = new SpecificationOptionQuery();
        specificationOptionQuery.createCriteria().andSpecIdEqualTo(id);
        /*删除规格下的规格选项*/
        specificationOptionDao.deleteByExample(specificationOptionQuery);
        /*删除规格并返回删除的数量*/
        return specificationDao.deleteByExample(specificationQuery);
    }

    @Override
    public int deleteSpecificationByIDs(String ids) {
        return 0;
    }

    @Override
    public List<Specification> querySpecificationAllSimple() {
        return specificationDao.selectByExample(null);
    }

}
