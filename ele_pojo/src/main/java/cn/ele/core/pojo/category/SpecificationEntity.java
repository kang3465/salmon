package cn.ele.core.pojo.category;

import java.io.Serializable;
import java.util.List;

public class SpecificationEntity implements Serializable {
    private Specification specification;

    private List<SpecificationOption> specificationOptionList;

    public SpecificationEntity() {

    }

    public SpecificationEntity(Specification specification, List<SpecificationOption> specificationOptionList) {
        this.specification = specification;
        this.specificationOptionList = specificationOptionList;
    }

    public Specification getSpecification() {
        return specification;
    }

    public void setSpecification(Specification specification) {
        this.specification = specification;
    }

    public List<SpecificationOption> getSpecificationOptionList() {
        return specificationOptionList;
    }

    public void setSpecificationOptionList(List<SpecificationOption> specificationOptionList) {
        this.specificationOptionList = specificationOptionList;
    }
}
