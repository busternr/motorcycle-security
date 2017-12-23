package org.elsys.motorcycle_security.dto;

import javax.validation.constraints.NotNull;

public class DeviceInfo {

    @NotNull
    private String deviceId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
