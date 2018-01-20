package org.elsys.motorcycle_security.models;


import javax.persistence.*;

@Entity
public class DataTransmiter {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @Column(name="Latitude")
    private double x;

    @Column(name="Longitude")
    private double y;

    @Column(name="Time")
    private long time;

    @Column(name="Date")
    private String date;

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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
