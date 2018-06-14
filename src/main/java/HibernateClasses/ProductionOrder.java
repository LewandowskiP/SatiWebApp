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

import java.sql.Timestamp;

/**
 * @author Przemysław
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    private Timestamp orderTime;
    @JsonIgnore
    private Timestamp completeTime;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getPositionInQueue() {
        return positionInQueue;
    }

    public void setPositionInQueue(int positionInQueue) {
        this.positionInQueue = positionInQueue;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public ProductionLine getProductionLine() {
        return productionLine;
    }

    public void setProductionLine(ProductionLine productionLine) {
        this.productionLine = productionLine;
    }

    public Employee getOrderedBy() {
        return orderedBy;
    }

    public void setOrderedBy(Employee orderedBy) {
        this.orderedBy = orderedBy;
    }

    public Employee getCompletedBy() {
        return completedBy;
    }

    public void setCompletedBy(Employee completedBy) {
        this.completedBy = completedBy;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

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
