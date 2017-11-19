package org.elsys.motorcycle_security.models;


import javax.persistence.*;

@Entity
public class DataTransmiter {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;



    @Column(name="Latitude")
    private long x;

    /// read what annotation
    @Column(name="Longitude")
    private long y;


    public long getX() {
        return x;
    }

    public void setX(long x) {
        this.x = x;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }
}
