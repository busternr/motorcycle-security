package org.elsys.motorcycle_security.dto;

import org.elsys.motorcycle_security.models.Device;

import javax.validation.constraints.NotNull;

public class DeviceInfo {

    @NotNull
    private long id;

    @NotNull
    private String deviceId;

    public DeviceInfo(long id, String deviceId) {
        this.id = id;
        this.deviceId = deviceId;
    }

    public DeviceInfo(Device device) {
        this(device.getId(), device.getDeviceId());
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
}
