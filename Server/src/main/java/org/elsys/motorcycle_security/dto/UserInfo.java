package org.elsys.motorcycle_security.dto;

import org.elsys.motorcycle_security.models.Device;
import org.elsys.motorcycle_security.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserInfo {
   private String email;
   private List<Device> devices = new ArrayList<>();

   public UserInfo(String email, List<Device> devices) {
        this.email = email;
        this.devices = devices;
    }

    public UserInfo(User user) {
        this(user.getEmail(), user.getUserDevices());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }
}
