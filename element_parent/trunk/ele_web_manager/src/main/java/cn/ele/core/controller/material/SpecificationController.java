package cn.ele.core.controller.material;

import cn.ele.core.pojo.category.Specification;
import cn.ele.core.pojo.entity.Result;
import cn.ele.core.service.category.SpecificationService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("specification")
public class SpecificationController {

    @Reference
    SpecificationService specificationService;

    /**
     * 返回分类
     * @return
     */
    @RequestMapping("querySpecificationAll")
    public List<Specification> querySpecificationAll(){
        List<Specification> specificationList=null;

        try {
            specificationList = specificationService.querySpecificationAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return specificationList;
    }

    @RequestMapping("deleteSpecification")
    @Secured("ROLE_ADMIN")
    public Result deleteSpecification(Integer specificationID){

        try {
            int i = specificationService.deleteSpecificationByID(specificationID);
            if (i==0){
                return new Result(false,"删除规格失败：没有数据被删除");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除规格失败："+e.getMessage());
        }
        return new Result(true,"删除规格成功");
    }

    @RequestMapping("addSpecification")
    public Result addSpecification(Specification entity){

        try {
            if (specificationService.addSpecification(entity)==0){
                return new Result(false,"添加规格失败:没有数据变化");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"添加规格分类错误："+e.getMessage());
        }
        return new Result(true,"添加规格成功");
    }

    @RequestMapping("saveSpecification")
    public Result saveSpecification(Specification entity){

        try {
            if (specificationService.saveSpecification(entity)==0) {
                return new Result(false,"更新规格失败:没有数据变化");
            };
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"更新规格失败："+e.getMessage());
        }

        return new Result(true,"更新规格成功");
    }

}
