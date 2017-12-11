package org.elsys.motorcycle_security.models;


import javax.persistence.*;

//Select * from DataTransmiter as dataTransmiter where dataTransmiter.deviceId == deviceId order by time
@Entity
public class DataTransmiter {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @Column(name="Latitude")
    private long x;

    @Column(name="Longitude")
    private long y;

    @Column(name="Time")
    private long time;

    @ManyToOne
    @JoinColumn(name="deviceId", nullable=false)
    private Device device;

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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
