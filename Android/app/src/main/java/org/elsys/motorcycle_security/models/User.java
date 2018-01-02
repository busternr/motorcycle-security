package org.elsys.motorcycle_security.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class User {
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("devices")
    @Expose
    private List<Device> devices = new ArrayList<>();

    public User(String email, String password, String deviceId) {
        this.email = email;
        this.password = password;
        Device device = new Device();
        device.setDeviceId(deviceId);
        this.getDevices().add(device);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}