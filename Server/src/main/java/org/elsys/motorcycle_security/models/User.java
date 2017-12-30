package org.elsys.motorcycle_security.models;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Users",uniqueConstraints={@UniqueConstraint(columnNames={"email"})})
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name="Email",unique=true)
    private String email;

    @Column(name="Password")
    private String password;

    @OneToMany(mappedBy="user")
    private List<Device> userDevices=new ArrayList<>();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email_) {
        this.email = email_;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password_) {
        this.password = password_;
    }

    public long getId() {
        return id;
    }

    public List<Device> getUserDevices() {
        return userDevices;
    }

    public void setUserDevices(List<Device> userDevices) {
        this.userDevices = userDevices;
    }
}