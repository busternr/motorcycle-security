package org.elsys.motorcycle_security.models;


import javax.persistence.*;

@Entity
public class DeviceStatus {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @Column(name="isParked")
    private boolean isParked;

    @Column(name="Timeout")
    private long timeout;

    @Column(name="Deviceid")
    private long deviceid;

    public long getId() {
        return id;
    }

    public DeviceStatus() {
        this.isParked = false;
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
