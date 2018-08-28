package cn.ele.core.pojo.material;

import java.io.Serializable;

public class MaterialDesc implements Serializable {
    private Long materialId;

    private String introduction;

    private String specificationItems;

    private String customAttributeItems;

    private String itemImages;

    private String packageList;

    private String saleService;

    private static final long serialVersionUID = 1L;

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public String getSpecificationItems() {
        return specificationItems;
    }

    public void setSpecificationItems(String specificationItems) {
        this.specificationItems = specificationItems == null ? null : specificationItems.trim();
    }

    public String getCustomAttributeItems() {
        return customAttributeItems;
    }

    public void setCustomAttributeItems(String customAttributeItems) {
        this.customAttributeItems = customAttributeItems == null ? null : customAttributeItems.trim();
    }

    public String getItemImages() {
        return itemImages;
    }

    public void setItemImages(String itemImages) {
        this.itemImages = itemImages == null ? null : itemImages.trim();
    }

    public String getPackageList() {
        return packageList;
    }

    public void setPackageList(String packageList) {
        this.packageList = packageList == null ? null : packageList.trim();
    }

    public String getSaleService() {
        return saleService;
    }

    public void setSaleService(String saleService) {
        this.saleService = saleService == null ? null : saleService.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", materialId=").append(materialId);
        sb.append(", introduction=").append(introduction);
        sb.append(", specificationItems=").append(specificationItems);
        sb.append(", customAttributeItems=").append(customAttributeItems);
        sb.append(", itemImages=").append(itemImages);
        sb.append(", packageList=").append(packageList);
        sb.append(", saleService=").append(saleService);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        MaterialDesc other = (MaterialDesc) that;
        return (this.getMaterialId() == null ? other.getMaterialId() == null : this.getMaterialId().equals(other.getMaterialId()))
            && (this.getIntroduction() == null ? other.getIntroduction() == null : this.getIntroduction().equals(other.getIntroduction()))
            && (this.getSpecificationItems() == null ? other.getSpecificationItems() == null : this.getSpecificationItems().equals(other.getSpecificationItems()))
            && (this.getCustomAttributeItems() == null ? other.getCustomAttributeItems() == null : this.getCustomAttributeItems().equals(other.getCustomAttributeItems()))
            && (this.getItemImages() == null ? other.getItemImages() == null : this.getItemImages().equals(other.getItemImages()))
            && (this.getPackageList() == null ? other.getPackageList() == null : this.getPackageList().equals(other.getPackageList()))
            && (this.getSaleService() == null ? other.getSaleService() == null : this.getSaleService().equals(other.getSaleService()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMaterialId() == null) ? 0 : getMaterialId().hashCode());
        result = prime * result + ((getIntroduction() == null) ? 0 : getIntroduction().hashCode());
        result = prime * result + ((getSpecificationItems() == null) ? 0 : getSpecificationItems().hashCode());
        result = prime * result + ((getCustomAttributeItems() == null) ? 0 : getCustomAttributeItems().hashCode());
        result = prime * result + ((getItemImages() == null) ? 0 : getItemImages().hashCode());
        result = prime * result + ((getPackageList() == null) ? 0 : getPackageList().hashCode());
        result = prime * result + ((getSaleService() == null) ? 0 : getSaleService().hashCode());
        return result;
    }
}