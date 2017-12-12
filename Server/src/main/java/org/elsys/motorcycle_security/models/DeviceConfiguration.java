package org.elsys.motorcycle_security.models;


import javax.persistence.*;

@Entity
public class DeviceConfiguration {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name="isParked")
    private boolean isParked;

    @Column(name="timeout")
    private long timeOut;

    @ManyToOne
    @JoinColumn(name="deviceId", nullable=false)
    private Device device;

    public DeviceConfiguration() {
        this.isParked = false;
        this.timeOut = 300000;
    }

    public boolean isParked() {
        return isParked;
    }

    public void setParked(boolean parked) {
        isParked = parked;
    }

    public long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}