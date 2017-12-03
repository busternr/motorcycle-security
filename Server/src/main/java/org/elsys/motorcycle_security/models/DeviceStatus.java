package org.elsys.motorcycle_security.models;


import javax.persistence.*;

@Entity
public class DeviceStatus {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @Column(name="isParked")
    private boolean isParked;

    @Column(name="Parked at Latitude")
    private long x;

    @Column(name="Parked at Longitude")
    private long y;

    @Column(name="Timeout")
    private long timeout;

    @Column(name="Deviceid")
    private long deviceid;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isParked() {
        return isParked;
    }

    public void setParked(boolean parked) {
        isParked = parked;
    }

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

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public long getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(long deviceid) {
        this.deviceid = deviceid;
    }
}
