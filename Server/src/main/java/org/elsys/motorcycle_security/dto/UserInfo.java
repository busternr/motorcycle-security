package org.elsys.motorcycle_security.dto;

import org.elsys.motorcycle_security.models.Device;
import org.elsys.motorcycle_security.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserInfo {
    private long id;
    private List<DeviceInfo> devices = new ArrayList<>();

    public UserInfo(long id) {
        this.id = id;
    }

    public UserInfo(User user) {
        this(user.getId());
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

    public List<DeviceInfo> getDevices() {
        return devices;
    }

    public void setDevices(List<DeviceInfo> devices) {
        this.devices = devices;
    }
}

