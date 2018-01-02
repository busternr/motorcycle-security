package org.elsys.motorcycle_security.dto;

import org.elsys.motorcycle_security.models.Device;
import org.elsys.motorcycle_security.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserInfo {
    private long id;
    private String email;
    private String password;
    private List<DeviceInfo> devices = new ArrayList<>();

    public UserInfo(long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public UserInfo(User user) {
        this(user.getId(), user.getEmail(), user.getPassword());

        for(Device device : user.getUserDevices()) {
            this.devices.add(new DeviceInfo(device));
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public List<DeviceInfo> getDevices() {
        return devices;
    }

    public void setDevices(List<DeviceInfo> devices) {
        this.devices = devices;
    }
}

