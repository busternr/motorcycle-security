package org.elsys.motorcycle_security.dto;

import org.elsys.motorcycle_security.models.DeviceConfiguration;

public class DeviceConfigurationInfo {
    private boolean isParked;
    private long timeOut;

    public DeviceConfigurationInfo(boolean isParked, long timeOut) {
        this.isParked = isParked;
        this.timeOut = timeOut;
    }

    public DeviceConfigurationInfo(DeviceConfiguration deviceConfiguration) {
        this(deviceConfiguration.isParked(), deviceConfiguration.getTimeOut());
    }

    public boolean isParked() {
        return isParked;
    }

    public void setParked(boolean parked) {
        isParked = parked;
    }

    public long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }
}
