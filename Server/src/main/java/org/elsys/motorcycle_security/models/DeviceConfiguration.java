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
    private long timeout;

    @ManyToOne
    @JoinColumn(name="deviceId", nullable=false)
    private Device device;

    public DeviceConfiguration() {
        this.isParked = false;
        this.timeout = 300000;
    }
    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}