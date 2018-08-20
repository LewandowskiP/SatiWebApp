package HibernateClasses;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class ProductionRaportPart {


    private int id;
    private Employee employee;
    private ProductType productType;
    private int labTestState;

    @Column(name = "labTestState")
    public int getLabTestState() {
        return labTestState;
    }

    public void setLabTestState(int labTestState) {
        this.labTestState = labTestState;
    }

    @Id
    @GeneratedValue
    @Column(name = "productionRaportPart_id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "employee_id")
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @ManyToOne
    @JoinColumn(name = "productType_id", nullable = false)
    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }
}
