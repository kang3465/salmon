package cn.ele.core.controller.material;

import cn.ele.core.entity.PageResult;
import cn.ele.core.entity.RespBean;
import cn.ele.core.entity.Result;
import cn.ele.core.pojo.category.Category;
import cn.ele.core.service.category.CategoryService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Reference
    CategoryService categoryService;

    /**
     * 返回分类
     * @return
     */
    @RequestMapping("queryCategoryByParentID")
    public RespBean queryCategoryByParentID(Long parentID){
        List<Category> categories=null;

        try {
            categories = categoryService.queryCategoryByParentID(parentID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.ok("数据加载成功",categories);
    }/**
     * 返回分类
     * @return
     */
    @RequestMapping("findOneByID")
    public RespBean findOneByID(Long id){
        Category categories=null;

        try {
            categories = categoryService.findOneByID(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.ok("数据加载成功",categories);
    }
    /**
     * 返回分类
     * @return
     */
    @RequestMapping("queryMaterialCategoryListWithCondition")
    public RespBean queryMaterialCategoryListWithCondition(Category entity, Integer pageNum, Integer pageSize, String keywords){
        PageResult categories=null;

        try {
            categories = categoryService.queryCategoryByParentID(entity,pageNum,pageSize,keywords);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.ok("查询成功",categories);
    }

    @RequestMapping("deleteCategory")
    @Secured("ROLE_ADMIN")
    public RespBean deleteCategory(String ids){
        int i;

        try {
            i = categoryService.deleteCategoryByIDs(ids);
            if (i==0){
                return RespBean.error("删除失败：没有数据被删除");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error(e.getMessage());
        }
        return RespBean.ok("删除成功,删除"+i+"条数据");
    }

    @RequestMapping("addCategroy")
    public Result addCategroy(Category entity){

        try {
            if (categoryService.addCategory(entity)==0){
                return new Result(false,"添加分类失败:没有数据变化");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"添加分类错误："+e.getMessage());
        }
        return new Result(true,"添加成功");
    }

    @RequestMapping("saveCategroy")
    public Result saveCategroy(Category entity){

        try {
            if (categoryService.saveCategory(entity)==0) {
                return new Result(false,"更新分类失败:没有数据变化");
            };
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"更新分类失败："+e.getMessage());
        }

        return new Result(true,"更新分类成功");
    }
    @RequestMapping("saveMaterialCategory")
    public RespBean saveMaterialCategory(Category entity){
        try {
        if (null!=entity.getId()||-1==entity.getId()){
                categoryService.addCategory(entity);
        }else {
            categoryService.saveCategory(entity);
        }
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("更新分类失败");
        }
        return RespBean.ok("更新分类成功");
    }

}
