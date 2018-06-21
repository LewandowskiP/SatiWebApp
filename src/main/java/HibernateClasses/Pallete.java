package HibernateClasses;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.sql.Timestamp;


@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Pallete {


    private int id;
    private Timestamp expiryDate;
    private Timestamp prodDate;
    private Float netto;
    private int quantity;
    private String batch;
    private int state;
    private ProductionRaportPart productionRaportPart;

    private String ean128Lot;
    private String ean128Pallete;
    private String ean128Num;

    @Id
    @GeneratedValue
    @Column(name = "pallete_id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "expiryDate")
    public Timestamp getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Timestamp expiryDate) {
        this.expiryDate = expiryDate;
    }


    @Column(name = "prodDate")
    public Timestamp getProdDate() {
        return prodDate;
    }

    public void setProdDate(Timestamp prodDate) {
        this.prodDate = prodDate;
    }


    @Column(name = "netto")
    public Float getNetto() {
        return netto;
    }

    public void setNetto(Float netto) {
        this.netto = netto;
    }


    @Column(name = "quantity")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    @Column(name = "batch")
    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }


    @Column(name = "state")
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @ManyToOne
    @JoinColumn(name = "productionRaportPart_id", nullable = false)
    public ProductionRaportPart getProductionRaportPart() {
        return productionRaportPart;
    }

    public void setProductionRaportPart(ProductionRaportPart productionRaportPart) {
        this.productionRaportPart = productionRaportPart;
    }


    @Column(name = "ean128Lot")
    public String getEan128Lot() {
        return ean128Lot;
    }

    public void setEan128Lot(String ean128Lot) {
        this.ean128Lot = ean128Lot;
    }

    @Column(name = "ean128Pallete")
    public String getEan128Pallete() {
        return ean128Pallete;
    }

    public void setEan128Pallete(String ean128Pallete) {
        this.ean128Pallete = ean128Pallete;
    }

    @Column(name = "ean128Num")
    public String getEan128Num() {
        return ean128Num;
    }

    public void setEan128Num(String ean128Num) {
        this.ean128Num = ean128Num;
    }
}
