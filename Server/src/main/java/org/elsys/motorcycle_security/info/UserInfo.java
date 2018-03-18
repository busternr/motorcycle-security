package org.elsys.motorcycle_security.info;

import org.elsys.motorcycle_security.models.Device;
import org.elsys.motorcycle_security.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserInfo {
    private long id;
    private String email;
    private List<DeviceInfo> devices = new ArrayList<>();

    public UserInfo() {
    }

    public UserInfo(long id, String email) {
        this.id = id;
        this.email = email;
    }

    public UserInfo(User user) {
        this(user.getId(), user.getEmail());
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

    public List<DeviceInfo> getDevices() {
        return devices;
    }

    public void setDevices(List<DeviceInfo> devices) {
        this.devices = devices;
    }
}

