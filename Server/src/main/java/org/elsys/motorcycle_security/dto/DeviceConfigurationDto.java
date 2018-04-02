package org.elsys.motorcycle_security.dto;


import javax.validation.constraints.NotNull;

public class DeviceConfigurationDto extends AbstractDto {
    @NotNull
    private long timeOut;

    @NotNull
    private long radius;

    @NotNull
    private boolean isParked;

    @NotNull
    private boolean isStolen;

    public DeviceConfigurationDto() {
    }

    public DeviceConfigurationDto(String deviceId) {
        this.deviceId = deviceId;
    }

    public DeviceConfigurationDto(String deviceId, long timeOut, long radius, boolean isParked, boolean isStolen) {
        this.deviceId = deviceId;
        this.timeOut = timeOut;
        this.radius = radius;
        this.isParked = isParked;
        this.isStolen = isStolen;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }

    public long getRadius() {
        return radius;
    }

    public void setRadius(long radius) {
        this.radius = radius;
    }

    public boolean isParked() {
        return isParked;
    }

    public void setParked(boolean parked) {
        isParked = parked;
    }

    public boolean isStolen() {
        return isStolen;
    }

    public void setStolen(boolean stolen) {
        isStolen = stolen;
    }
}