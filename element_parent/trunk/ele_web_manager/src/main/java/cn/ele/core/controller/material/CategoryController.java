package cn.ele.core.controller.material;

import cn.ele.core.pojo.category.Category;
import cn.ele.core.pojo.entity.Result;
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
    @RequestMapping("queryCategory")
    public List<Category> queryCategoryByParentID(Integer parentID){
        List<Category> categories=null;

        try {
            categories = categoryService.queryCategoryByParentID(parentID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }

    @RequestMapping("deleteCategory")
    @Secured("ROLE_ADMIN")
    public Result deleteCategory(Integer categoryID){

        try {
            int i = categoryService.deleteCategoryByID(categoryID);
            if (i==0){
                return new Result(false,"删除失败：没有数据被删除");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败："+e.getMessage());
        }
        return new Result(true,"删除成功");
    }

}
