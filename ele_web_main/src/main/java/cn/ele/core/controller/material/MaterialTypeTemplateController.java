package cn.ele.core.controller.material;

import cn.ele.core.entity.PageResult;
import cn.ele.core.entity.RespBean;
import cn.ele.core.entity.Result;
import cn.ele.core.pojo.category.MaterialTypeTemplate;
import cn.ele.core.service.category.MaterialTypeTemplateSrevice;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kang
 */
@RestController
@RequestMapping("materialTypeTemplate")
public class MaterialTypeTemplateController {


    @Reference
    MaterialTypeTemplateSrevice materialTypeTemplateSrevice;
    @RequestMapping("queryAllByPage")
    public RespBean queryAllByPage(Integer pageNum, Integer pageSize) {
        PageResult pageResult = null;

        try {
            pageResult = materialTypeTemplateSrevice.queryAllByPage(pageNum, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return RespBean.ok("查询成功",pageResult);
    }
    @RequestMapping("add")
    public Result add(MaterialTypeTemplate materialTypeTemplate) {

        try {
            if(materialTypeTemplateSrevice.add(materialTypeTemplate)==0){
                    return new Result(false,"添加模板数据失败:添加数量为0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"添加模板数据出现异常，添加失败");
        }

        return new Result(false,"添加模板数据出现异常，添加失败");
    }
    @RequestMapping("queryOne")
    public MaterialTypeTemplate queryOne(Long id) {
        MaterialTypeTemplate materialTypeTemplate =null;
        try {
            materialTypeTemplate = materialTypeTemplateSrevice.queryByID(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return materialTypeTemplate;
    }

}
