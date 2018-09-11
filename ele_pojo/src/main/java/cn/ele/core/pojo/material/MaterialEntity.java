package cn.ele.core.pojo.material;

import java.io.Serializable;

public class MaterialEntity implements Serializable {
    private Material material;
    private MaterialDesc materialDesc;

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public MaterialDesc getMaterialDesc() {
        return materialDesc;
    }

    public void setMaterialDesc(MaterialDesc materialDesc) {
        this.materialDesc = materialDesc;
    }
}
