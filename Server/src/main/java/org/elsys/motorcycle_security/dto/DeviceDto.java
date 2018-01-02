package org.elsys.motorcycle_security.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DeviceDto {

    @NotNull
    @Size(min = 6)
    private String deviceId;

    @NotNull
    long userId;

    public DeviceDto(){}

    public DeviceDto(String deviceId, long userId) {
        this.deviceId = deviceId;
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}