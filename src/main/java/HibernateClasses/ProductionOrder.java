package HibernateClasses;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sati.web.app.States;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * @author Przemysław
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class ProductionOrder implements Comparable {

    private int id;

    private int positionInQueue;

    private Integer quantity;

    private String otherInfo;

    private ProductType productType;
    private ProductionLine productionLine;
    @JsonIgnore
    private Employee orderedBy;
    @JsonIgnore
    private Employee completedBy;

    private Timestamp deadline;
    private Timestamp orderTime;
    @JsonIgnore
    private Timestamp completeTime;

    private boolean important;

    private int state;


    public void makeOrder(ProductionLine productionLine,
                          ProductType productType,
                          Employee orderedBy,
                          Integer quantity,
                          String otherInfo,
                          int positionInQueue) {
        this.productionLine = productionLine;
        this.productType = productType;
        this.orderedBy = orderedBy;
        this.quantity = quantity;
        this.otherInfo = otherInfo;
        this.state = States.PRODUCTION_ORDER_ORDERED;
        this.positionInQueue = positionInQueue;
        this.orderTime = new Timestamp(System.currentTimeMillis());
    }


    @Column(name = "important")
    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    @Column(name = "deadline")
    public Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
    }

    public void upQueue() {
        this.positionInQueue--;
    }

    public void downQueue() {
        this.positionInQueue++;
    }

    public void completeOrder(Employee completedBy) {
        this.completedBy = completedBy;
        this.completeTime = new Timestamp(System.currentTimeMillis());
        this.state = States.PRODUCTION_ORDER_COMPLETED;
    }

    @Id
    @Column(name = "productionOrder_id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "state")
    public int getState() {
        return state;
    }


    public void setState(int state) {
        this.state = state;
    }

    @Column(name = "positionInQueue")
    public int getPositionInQueue() {
        return positionInQueue;
    }

    public void setPositionInQueue(int positionInQueue) {
        this.positionInQueue = positionInQueue;
    }

    @Column(name = "quantity")
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Column(name = "otherInfo", length = 50)
    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    @ManyToOne
    @JoinColumn(name = "productType", nullable = false)
    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    @ManyToOne
    @JoinColumn(name = "productionLine", nullable = false)
    public ProductionLine getProductionLine() {
        return productionLine;
    }

    public void setProductionLine(ProductionLine productionLine) {
        this.productionLine = productionLine;
    }

    @ManyToOne
    @JoinColumn(name = "orderedBy", nullable = false)
    public Employee getOrderedBy() {
        return orderedBy;
    }

    public void setOrderedBy(Employee orderedBy) {
        this.orderedBy = orderedBy;
    }

    @ManyToOne
    @JoinColumn(name = "completedBy", nullable = true)
    public Employee getCompletedBy() {
        return completedBy;
    }

    public void setCompletedBy(Employee completedBy) {
        this.completedBy = completedBy;
    }

    @Column(name = "orderTime")
    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    @Column(name = "completeTime")
    public Timestamp getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Timestamp completeTime) {
        this.completeTime = completeTime;
    }

    @Override
    public int compareTo(Object o) {
        return this.positionInQueue - ((ProductionOrder) o).getPositionInQueue();
    }

    @Override
    public String toString() {
        return positionInQueue + 1 + ". " + productType.getProductName() + " " + quantity + " Kg/Szt. Uwagi:" + otherInfo;
    }

}
