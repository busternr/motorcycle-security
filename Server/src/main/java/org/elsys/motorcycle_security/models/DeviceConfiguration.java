package org.elsys.motorcycle_security.models;


import javax.persistence.*;

@Entity
public class DeviceConfiguration {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name="isParked",nullable=false)
    private boolean isParked;

    @Column(name="isStolen",nullable=false)
    private boolean isStolen;

    @Column(name="timeout")
    private long timeOut;

    @OneToOne
    @JoinColumn(name="deviceId", nullable=false)
    private Device device;

    public DeviceConfiguration() {
        this.isParked = false;
        this.isStolen = false;
        this.timeOut = 150000;
    }

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

    public boolean isStolen() {
        return isStolen;
    }

    public void setStolen(boolean stolen) {
        isStolen = stolen;
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