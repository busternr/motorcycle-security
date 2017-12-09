package org.elsys.motorcycle_security.models;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Devices {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name="userid", nullable=false)
    private Users user;

    @Column(name="DeviceId",nullable = false)
    private String deviceId;

    @OneToMany(mappedBy="device")
    private List<DataTransmiter> transmiterData = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}