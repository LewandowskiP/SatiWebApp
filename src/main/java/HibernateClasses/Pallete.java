package HibernateClasses;


import java.sql.Timestamp;


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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Timestamp expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Timestamp getProdDate() {
        return prodDate;
    }

    public void setProdDate(Timestamp prodDate) {
        this.prodDate = prodDate;
    }

    public Float getNetto() {
        return netto;
    }

    public void setNetto(Float netto) {
        this.netto = netto;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public ProductionRaportPart getProductionRaportPart() {
        return productionRaportPart;
    }

    public void setProductionRaportPart(ProductionRaportPart productionRaportPart) {
        this.productionRaportPart = productionRaportPart;
    }

    public String getEan128Lot() {
        return ean128Lot;
    }

    public void setEan128Lot(String ean128Lot) {
        this.ean128Lot = ean128Lot;
    }

    public String getEan128Pallete() {
        return ean128Pallete;
    }

    public void setEan128Pallete(String ean128Pallete) {
        this.ean128Pallete = ean128Pallete;
    }

    public String getEan128Num() {
        return ean128Num;
    }

    public void setEan128Num(String ean128Num) {
        this.ean128Num = ean128Num;
    }
}
