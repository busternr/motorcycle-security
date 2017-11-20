package org.elsys.motorcycle_security.models;


import javax.persistence.*;

@Entity
public class Users {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @Column(name="Username")
    private String username;

    @Column(name="Email")
    private String email;

    @Column(name="Password")
    private String password;

    @Column(name="Deviceid")
    private long deviceid;

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

    public long getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(long deviceid_) {
        this.deviceid = deviceid_;
    }

}