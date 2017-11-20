package org.elsys.motorcycle_security.models;


import javax.persistence.*;

@Entity
public class Devices {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name="Userid")
    private long userid;

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid_) {
        this.userid = userid_;
    }

}