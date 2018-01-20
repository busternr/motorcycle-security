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

    @Column(name="DeviceId",nullable = false,unique=true)
    private String deviceId;

    @Column(name="ParkedLatitude")
    private double parkedX;

    @Column(name="ParkedLongitude")
    private double parkedY;

    @OneToMany(mappedBy="device")
    private List<DataTransmiter> transmiterData = new ArrayList<>();

    @OneToOne(mappedBy="device")
    private DeviceConfiguration deviceConfiguration;

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

    public double getParkedX() {
        return parkedX;
    }

    public void setParkedX(double parkedX) {
        this.parkedX = parkedX;
    }

    public double getParkedY() {
        return parkedY;
    }

    public void setParkedY(double parkedY) {
        this.parkedY = parkedY;
    }
}