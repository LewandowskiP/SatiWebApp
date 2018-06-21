package HibernateClasses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "employees")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Employee {

    private int id;

    @JsonIgnore
    private String firstName;


    @JsonIgnore
    private String lastName;


    @JsonIgnore
    @Column(name = "lastName", length = 20)
    private String login;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private int jobPosition;
    private String employeeID;

    @Column(name = "employeeID", length = 6)
    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    @Id
    @GeneratedValue
    @Column(name = "employee_id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Column(name = "firstName", length = 20)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    @Column(name = "lastName", length = 20)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    @Column(name = "login", length = 20)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Column(name = "password", length = 20)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Column(name = "jobPosition")
    public int getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(int jobPosition) {
        this.jobPosition = jobPosition;
    }

    @Override
    public String toString() {
        return employeeID;
    }
}
