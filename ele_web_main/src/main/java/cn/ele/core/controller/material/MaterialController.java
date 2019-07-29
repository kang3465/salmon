package cn.ele.core.controller.material;

import cn.ele.core.entity.RespBean;
import cn.ele.core.entity.Result;
import cn.ele.core.pojo.material.MaterialEntity;
import cn.ele.core.service.material.MaterialService;
import cn.ele.core.util.UserUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
    public RespBean add(MaterialEntity materialEntity) {

        if (materialEntity != null && materialEntity.getMaterialDesc() != null && materialEntity.getMaterialDesc().getPackageList() != null) {
            JSONArray jsonArray = JSON.parseArray(materialEntity.getMaterialDesc().getPackageList());
            if (jsonArray.size() > 0) {
                materialEntity.getMaterial().setIconurl((String) jsonArray.get(0));
            }
        }
        materialEntity.getMaterial().setUserId(UserUtils.getCurrentUser().getId());
        try {
            if (materialService.add(materialEntity) == 0) {
                return RespBean.error("添加素材失败：添加0条素材");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("添加失败");
        }
        return RespBean.ok("添加成功");
    }

    @RequestMapping("queryOneByID")
    public MaterialEntity queryOneByID(Integer id) {
        try {
            return materialService.queryOneByID(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    } @RequestMapping("findOneByID")
    public RespBean findOneByID(Long id) {
        MaterialEntity materialEntity = null;
        try {
            materialEntity =  materialService.findOneByID(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.ok("",materialEntity);
    }


    @RequestMapping("save")
    public Result save(@RequestBody MaterialEntity materialEntity) {
          try {
            if (materialService.update(materialEntity) == 0) {
                return new Result(false, "保存素材失败：保存0条素材");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "保存素材出现异常：" + e.getMessage());
        }

        return new Result(true, "保存素材成功");
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
    public RespBean queryByPage(Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageSize == null) {
            pageNum = 1;
            pageSize = 10;
        }
        try {
            return RespBean.ok("查询成功", materialService.queryAllByPage(pageNum, pageSize));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.ok("查询失败");
    }
    @RequestMapping("querySelfByPage")
    public RespBean querySelfByPage(Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageSize == null) {
            pageNum = 1;
            pageSize = 10;
        }
        try {
            return RespBean.ok("查询成功", materialService.querySelfByPage(pageNum, pageSize,UserUtils.getCurrentUser().getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.ok("查询失败");
    }

}
