package org.elsys.motorcycle_security.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DeviceDto {

    @NotNull
    @Size(min = 6)
    private String deviceId;

    @NotNull
    long userId;

    long parkedX;

    long parkedY;

    public DeviceDto(){}

    public DeviceDto(String deviceId, long userId, long parkedX, long parkedY) {
        this.deviceId = deviceId;
        this.userId = userId;
        this.parkedX = parkedX;
        this.parkedY = parkedY;
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

    public long getParkedX() {
        return parkedX;
    }

    public void setParkedX(long parkedX) {
        this.parkedX = parkedX;
    }

    public long getParkedY() {
        return parkedY;
    }

    public void setParkedY(long parkedY) {
        this.parkedY = parkedY;
    }
}