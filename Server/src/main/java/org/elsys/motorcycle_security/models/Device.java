package org.elsys.motorcycle_security.models;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Devices")
public class Device {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name="userid", nullable=false)
    private User user;

    @Column(name="DeviceId",nullable = false)
    private String deviceId;

    @OneToMany(mappedBy="device")
    private List<DataTransmiter> transmiterData = new ArrayList<>();

    @OneToMany(mappedBy="device")
    private List<DeviceConfiguration> deviceConfigurations = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}