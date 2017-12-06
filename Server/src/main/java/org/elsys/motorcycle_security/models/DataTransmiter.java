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
    private String time;

    @Column(name="Deviceid")
    private long deviceId;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }
}
