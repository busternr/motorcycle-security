package org.elsys.motorcycle_security.models;


import javax.persistence.*;

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
    private long deviceid;

    public long getX() {
        return x;
    }

    public void setX(long x_) {
        this.x = x_;
    }

    public long getY() {
        return y;
    }

    public void setY(long y_) {
        this.y = y_;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time_) {
        this.time = time_;
    }

    public long getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(long deviceid_) {
        this.deviceid = deviceid_;
    }

}
