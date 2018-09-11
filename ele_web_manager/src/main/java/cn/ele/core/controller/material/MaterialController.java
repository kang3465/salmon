package cn.ele.core.controller.material;

import cn.ele.core.pojo.entity.PageResult;
import cn.ele.core.pojo.entity.Result;
import cn.ele.core.pojo.material.MaterialEntity;
import cn.ele.core.service.material.MaterialService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kang
 */
@RestController
@RequestMapping("material")
public class MaterialController {

    @Reference
    MaterialService materialService;
    @RequestMapping("add")
    public Result add(@RequestBody MaterialEntity materialEntity){

        try {
            if (materialService.add(materialEntity)==0) {
                return new Result(false ,"添加素材失败：添加0条素材");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false ,"添加素材出现异常："+e.getMessage());
        }

        return new Result(true ,"添加素材成功");
    }
    @RequestMapping("queryOneByID")
    public MaterialEntity queryOneByID(Integer id){
        try {
            return materialService.queryOneByID(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @RequestMapping("save")
    public Result save(@RequestBody MaterialEntity materialEntity){

        try {
            if (materialService.update(materialEntity)==0) {
                return new Result(false ,"保存素材失败：保存0条素材");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false ,"保存素材出现异常："+e.getMessage());
        }

        return new Result(true ,"保存素材成功");
    }

    @RequestMapping("delete")
    public Result delete(@RequestBody Integer id) {

        try {
            if (materialService.deleteByID(id) == 0) {
                return new Result(false, "删除素材失败：删除0条素材");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除素材出现异常：" + e.getMessage());
        }

        return new Result(true, "删除素材成功");
    }
    @RequestMapping("queryByPage")
    public PageResult queryByPage(Integer pageNum, Integer pageSize){
        try {
            return materialService.queryAllByPage(pageNum,pageNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
