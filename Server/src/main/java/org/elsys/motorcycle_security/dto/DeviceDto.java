package org.elsys.motorcycle_security.dto;


import javax.validation.constraints.NotNull;

public class DeviceDto {


    @NotNull
    private String deviceId;

    public DeviceDto(){}

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

}