package org.elsys.motorcycle_security.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niko on 12/16/2017.
 */


public class UserDto {
    @NotNull
    @Size(min = 1)
    private String email;

    @NotNull
    @Size(min = 6)
    private String password;

    @NotNull
    @Size(min=1)
    @Valid
    private List<DeviceDto> devices = new ArrayList<>();

    public UserDto(){}
    public UserDto(String email, String password, String deviceId) {
        this.email = email;
        this.password = password;
        DeviceDto device = new DeviceDto();
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

    public List<DeviceDto> getDevices() {
        return devices;
    }

    public void setDevices(List<DeviceDto> devices) {
        this.devices = devices;
    }

}