package org.elsys.motorcycle_security.models;


import javax.persistence.*;

@Entity
public class DeviceSettings {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @Column(name="isParked")
    private boolean isParked;

    @Column(name="Timeout")
    private long timeout;

    @Column(name="Deviceid")
    private long deviceId;


    public DeviceSettings() {
        this.isParked = false;
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
        return deviceId;
    }

    public void setDeviceid(long deviceId) {
        this.deviceId = deviceId;
    }
}
