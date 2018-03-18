package org.elsys.motorcycle_security.info;

import org.elsys.motorcycle_security.models.DeviceConfiguration;

public class DeviceConfigurationInfo {
    private boolean isParked;
    private boolean isStolen;
    private long timeOut;

    public DeviceConfigurationInfo(boolean isParked, boolean isStolen, long timeOut) {
        this.isParked = isParked;
        this.isStolen = isStolen;
        this.timeOut = timeOut;
    }

    public DeviceConfigurationInfo(DeviceConfiguration deviceConfiguration) {
        this(deviceConfiguration.isParked(), deviceConfiguration.isStolen(), deviceConfiguration.getTimeOut());
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

    public long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }
}
