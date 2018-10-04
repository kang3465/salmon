//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.ele.core.pojo.material;

import java.io.Serializable;

public class MaterialEntity implements Serializable {
    private Material material;
    private MaterialDesc materialDesc;

    public MaterialEntity() {
    }

    public Material getMaterial() {
        return this.material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public MaterialDesc getMaterialDesc() {
        return this.materialDesc;
    }

    public void setMaterialDesc(MaterialDesc materialDesc) {
        this.materialDesc = materialDesc;
    }
}
