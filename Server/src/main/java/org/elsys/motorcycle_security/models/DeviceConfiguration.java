package org.elsys.motorcycle_security.models;


import javax.persistence.*;

@Entity
public class DeviceConfiguration {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name="isParked")
    private boolean isParked;

    @Column(name="Timeout")
    private long timeout;

    public DeviceConfiguration() {
        this.isParked = false;
        this.timeout = 300000;
    }
}