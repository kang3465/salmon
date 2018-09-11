package cn.ele.core.service.category;

import cn.ele.core.dao.category.MaterialTypeTemplateDao;
import cn.ele.core.pojo.category.MaterialTypeTemplate;
import cn.ele.core.pojo.category.MaterialTypeTemplateQuery;
import cn.ele.core.pojo.entity.PageResult;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class MaterialTypeTemplateSreviceImpl implements MaterialTypeTemplateSrevice{

    @Autowired
    MaterialTypeTemplateDao materialTypeTemplateDao;
    /**
     * 查询所有模板
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<MaterialTypeTemplate> queryAll() throws Exception {
        return null;
    }

    /**
     * 分页查询所有模板
     *
     * @param pageNum  第几页
     * @param pageSize 每页几条数据
     * @return PageResult 分页实体
     * @throws Exception
     */
    @Override
    public PageResult queryAllByPage(Integer pageNum, Integer pageSize) throws Exception {
        PageHelper.startPage(pageNum,pageSize);

        Page<MaterialTypeTemplate> page = (Page<MaterialTypeTemplate>) materialTypeTemplateDao.selectByExample(null);

        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 根据id查询模板信息
     *
     * @param id 模板ID
     * @return 查询到的模板对象，未查找到返回null
     * @throws Exception
     */
    @Override
    public MaterialTypeTemplate queryByID(Long id) throws Exception {
        MaterialTypeTemplateQuery materialTypeTemplateQuery = new MaterialTypeTemplateQuery();
        materialTypeTemplateQuery.createCriteria().andIdEqualTo(id);
        return materialTypeTemplateDao.selectByExample(materialTypeTemplateQuery).get(0);
    }

    /**
     * 新增一条素材模板数据
     *
     * @param materialTypeTemplate
     * @return
     * @throws Exception
     */
    @Override
    public int add(MaterialTypeTemplate materialTypeTemplate) throws Exception {
        return 0;
    }

    /**
     * 更新一条素材模板数据
     *
     * @param materialTypeTemplate
     * @return 跟新的条数
     * @throws Exception
     */
    @Override
    public int update(MaterialTypeTemplate materialTypeTemplate) throws Exception {
        return 0;
    }

    /**
     * 根据id删除一条数据
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public int deleteByID(Integer id) throws Exception {
        return 0;
    }

}
