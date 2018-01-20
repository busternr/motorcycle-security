package org.elsys.motorcycle_security.dto;

import org.elsys.motorcycle_security.models.Device;

import javax.validation.constraints.NotNull;

public class DeviceInfo {

    @NotNull
    private long id;

    @NotNull
    private String deviceId;

    private double parkedX;

    private double parkedY;

    public DeviceInfo(long id, String deviceId, double parkedX, double parkedY) {
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
