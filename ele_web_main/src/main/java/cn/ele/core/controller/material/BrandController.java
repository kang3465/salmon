package cn.ele.core.controller.material;

import cn.ele.core.entity.PageResult;
import cn.ele.core.entity.Result;
import cn.ele.core.pojo.category.SpecificationEntity;
import cn.ele.core.service.category.SpecificationService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("brand")
public class BrandController {

    @Reference
    SpecificationService specificationService;

    /**
     * 返回分类
     * @return
     */
    @RequestMapping("queryOne")
    public SpecificationEntity queryOne(Long id){
        SpecificationEntity entity=null;

        try {
            entity = specificationService.queryOne(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }

    /**
     * 返回分类
     *
     * @return
     */
    @RequestMapping("querySpecificationAllByPage")
    public PageResult querySpecificationAllByPage(Integer pageNum, Integer pageSize) {
        PageResult pageResult = null;

        try {
            pageResult = specificationService.querySpecificationAllByPage(pageNum, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageResult;
    }

    @RequestMapping("deleteSpecificationByID")
    @Secured("ROLE_ADMIN")
    public Result deleteSpecificationByID(Long id){

        try {
            int i = specificationService.deleteSpecificationByID(id);
            if (i==0){
                return new Result(false,"删除规格失败：没有数据被删除");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除规格失败："+e.getMessage());
        }
        return new Result(true,"删除规格成功");
    }
    @RequestMapping("deleteSpecificationByIDs")
    @Secured("ROLE_ADMIN")
    public Result deleteSpecificationByIDs(Long[] ids){
        try {
            int count =0;
            for (int i = 0; i <ids.length ; i++) {
                count+=specificationService.deleteSpecificationByID(ids[i]);
            }
            if (count!=ids.length){
                return new Result(false,"删除规格失败：只删除了 "+count+" 条数据。");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除规格失败，出现异常："+e.getMessage());
        }
        return new Result(true,"删除规格成功");
    }

    @RequestMapping("addSpecification")
    public Result addSpecification(@RequestBody(required=false) SpecificationEntity entity){

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
    public Result saveSpecification(@RequestBody(required=false) SpecificationEntity entity){

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
