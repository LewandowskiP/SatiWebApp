package HibernateClasses;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
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

    @Column( name = "roast")
    public boolean isRoast() {
        return roast;
    }

    public void setRoast(boolean roast) {
        this.roast = roast;
    }

    @Id
    @GeneratedValue
    @Column(name ="productionLine_id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "line",length = 30)
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

