package cn.ele.core.service.material;

import cn.ele.core.dao.material.MaterialDao;
import cn.ele.core.dao.material.MaterialDescDao;
import cn.ele.core.entity.PageResult;
import cn.ele.core.pojo.material.Material;
import cn.ele.core.pojo.material.MaterialDescQuery;
import cn.ele.core.pojo.material.MaterialEntity;
import cn.ele.core.pojo.material.MaterialQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    MaterialDao materialDao;
    @Autowired
    MaterialDescDao materialDescDao;

    @Override
    public int add(MaterialEntity materialEntity) throws Exception {
        materialEntity.getMaterial().setId(null);
        Material material = materialEntity.getMaterial();
        materialDao.insertSelective(material);
        materialEntity.getMaterialDesc().setMaterialId(material.getId());
        materialDescDao.insertSelective(materialEntity.getMaterialDesc());
//        materialDescDao.insertSelective(materialEntity.getMaterialDesc());
        return 1;
    }

    @Override
    public int update(MaterialEntity materialEntity) throws Exception {
        return 0;
    }

    @Override
    public int deleteByID(Integer id) throws Exception {
        return 0;
    }

    @Override
    public int deleteByIDs(String ids) throws Exception {
        return 0;
    }

    @Override
    public MaterialEntity queryOneByID(Integer id) throws Exception {
        return null;
    }

    @Override
    public PageResult queryAllByPage(Integer pageNum, Integer pageSize) throws Exception {
        PageHelper.startPage(pageNum, pageSize);
        Page<Material> page = (Page<Material>) materialDao.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public MaterialEntity findOneByID(Long id) {
        MaterialEntity materialEntity = new MaterialEntity();
        MaterialQuery materialQuery = new MaterialQuery();
        materialQuery.createCriteria().andIdEqualTo(id);
        materialEntity.setMaterial(materialDao.selectByExample(materialQuery).get(0));

        MaterialDescQuery materialDescQuery = new MaterialDescQuery();
        materialDescQuery.createCriteria().andMaterialIdEqualTo(id);
        materialEntity.setMaterialDesc(materialDescDao.selectByExample(materialDescQuery).get(0));
        return materialEntity;
    }

    @Override
    public Object querySelfByPage(Integer pageNum, Integer pageSize,Long userId) {
        PageHelper.startPage(pageNum, pageSize);
        MaterialQuery materialQuery = new MaterialQuery();
        materialQuery.createCriteria().andUserIdEqualTo(userId);
        Page<Material> page = (Page<Material>) materialDao.selectByExample(materialQuery);
        return new PageResult(page.getTotal(), page.getResult());
    }
}
