package org.elsys.motorcycle_security.dto;

import org.elsys.motorcycle_security.models.Device;

import javax.validation.constraints.NotNull;

public class DeviceInfo {

    @NotNull
    private long id;

    @NotNull
    private String deviceId;

    @NotNull
    private long upTime;

    public DeviceInfo(long id, String deviceId, long upTime) {
        this.id = id;
        this.deviceId = deviceId;
        this.upTime = upTime;
    }

    public DeviceInfo(Device device) {
        this(device.getId(), device.getDeviceId(), device.getUpTime());
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

    public long getUpTime() {
        return upTime;
    }

    public void setUpTime(long upTime) {
        this.upTime = upTime;
    }
}
