package cn.ele.core.pojo.good;


import java.io.Serializable;
import java.util.List;

public class GoodsEntity implements Serializable {

    private Goods goods;

    private GoodsDesc goodsDesc;


    public GoodsEntity(Goods goods, GoodsDesc goodsDesc) {
        this.goods = goods;
        this.goodsDesc = goodsDesc;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public GoodsDesc getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(GoodsDesc goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

}
