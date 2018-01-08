package org.elsys.motorcycle_security.business.logic;

import org.elsys.motorcycle_security.dto.DeviceConfigurationInfo;

public interface DeviceConfiguration {
    DeviceConfigurationInfo getDeviceConfiguration(String deviceId);

    void updateTimeOut(String deviceId, long timeOut);

    void updateParkingStatus(String deviceId, boolean isParked);
}
