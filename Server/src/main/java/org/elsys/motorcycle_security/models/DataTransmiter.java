package org.elsys.motorcycle_security.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class DataTransmiter {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @Column(name="Latitude")
    private double x;

    @Column(name="Longitude")
    private double y;

    @Column(name="Date")
    private Date date;

    @ManyToOne
    @JoinColumn(name="deviceId", nullable=false)
    private Device device;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
