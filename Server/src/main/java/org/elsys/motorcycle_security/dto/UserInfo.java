package org.elsys.motorcycle_security.dto;

import org.elsys.motorcycle_security.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserInfo {
   private String userName;
   private String email;
   private List<DeviceInfo> devices = new ArrayList<DeviceInfo>() {};

    public UserInfo(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public UserInfo(User user) {
        this(user.getUsername(), user.getEmail());
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
