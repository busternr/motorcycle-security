package org.elsys.motorcycle_security.dto;

import org.elsys.motorcycle_security.models.Device;

import javax.validation.constraints.NotNull;

public class DeviceInfo {

    @NotNull
    private long id;

    @NotNull
    private String deviceId;

    private long parkedX;

    private long parkedY;

    public DeviceInfo(long id, String deviceId, long parkedX, long parkedY) {
        this.id = id;
        this.deviceId = deviceId;
        this.parkedX = parkedX;
        this.parkedY = parkedY;
    }

    public DeviceInfo(Device device) {
        this(device.getId(), device.getDeviceId(), device.getParkedX(), device.getParkedY());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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
