package HibernateClasses;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductionRaportPart {

    private int id;
    private Employee emp;
    private ProductType productType;
    private int labTestState;


    public int getLabTestState() {
        return labTestState;
    }

    public void setLabTestState(int labTestState) {
        this.labTestState = labTestState;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee getEmp() {
        return emp;
    }

    public void setEmp(Employee emp) {
        this.emp = emp;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }
}
