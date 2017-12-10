package org.elsys.motorcycle_security.models;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @Column(name="Username")
    private String username;

    @Column(name="Email")
    private String email;

    @Column(name="Password")
    private String password;

    //"user" trqbvda e imeto na promenlivata vuv deviceids
    @OneToMany(mappedBy="user")
    private List<Devices> userDevices=new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username_) {
        this.username = username_;
    }

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

    public List<Devices> getUserDevices() {
        return userDevices;
    }

    public void setUserDevices(List<Devices> userDevices) {
        this.userDevices = userDevices;
    }
}