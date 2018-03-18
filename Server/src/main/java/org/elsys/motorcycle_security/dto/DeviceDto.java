package org.elsys.motorcycle_security.dto;


import javax.validation.constraints.NotNull;

public class DeviceDto extends AbstractDto {
    @NotNull
    long userId;

    @NotNull
    double parkedX;

    @NotNull
    double parkedY;

    public DeviceDto() {
    }

    public DeviceDto(String deviceId) {
        this.deviceId = deviceId;
    }

    public DeviceDto(String deviceId, long userId, double parkedX, double parkedY) {
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

    public double getParkedX() {
        return parkedX;
    }

    public void setParkedX(double parkedX) {
        this.parkedX = parkedX;
    }

    public double getParkedY() {
        return parkedY;
    }

    public void setParkedY(double parkedY) {
        this.parkedY = parkedY;
    }
}