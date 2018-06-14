package HibernateClasses;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductionLine implements Comparable {

    private int id;
    private String line;
    @JsonIgnore
    private boolean roast;

    public ProductionLine(int id, String line) {
        this.id = id;
        this.line = line;
    }

    public ProductionLine() {
    }

    public boolean isRoast() {
        return roast;
    }

    public void setRoast(boolean roast) {
        this.roast = roast;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    @Override
    public String toString() {
        return line;
    }

    public int compareTo(Object o) {
        return this.toString().compareTo(o.toString());
    }

}

