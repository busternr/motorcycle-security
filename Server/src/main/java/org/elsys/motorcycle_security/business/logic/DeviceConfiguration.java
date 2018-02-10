package org.elsys.motorcycle_security.business.logic;

import org.elsys.motorcycle_security.dto.DeviceConfigurationDto;
import org.elsys.motorcycle_security.dto.DeviceConfigurationInfo;

public interface DeviceConfiguration {
    DeviceConfigurationInfo getDeviceConfiguration(String deviceId);

    void updateTimeOut(DeviceConfigurationDto deviceConfigurationDto);

    void updateParkingStatus(DeviceConfigurationDto deviceConfigurationDt);

    void updateStolenStatus(DeviceConfigurationDto deviceConfigurationDt);
}
