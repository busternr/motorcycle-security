package org.elsys.motorcycle_security.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class DataTransmitter {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @Column(name="Latitude")
    private double x;

    @Column(name="Longitude")
    private double y;

    @Column(name="Speed")
    private double speed;

    @Column(name="Date")
    private Date date;

    @ManyToOne
    @JoinColumn(name="deviceId", nullable=false)
    private Device device;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
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
