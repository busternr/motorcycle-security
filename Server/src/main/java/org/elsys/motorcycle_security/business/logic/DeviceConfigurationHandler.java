package org.elsys.motorcycle_security.business.logic;

import org.elsys.motorcycle_security.dto.DeviceConfigurationInfo;
import org.elsys.motorcycle_security.models.DeviceConfiguration;
import org.elsys.motorcycle_security.repository.DeviceConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeviceConfigurationHandler {
    @Autowired
    private DeviceConfigurationRepository deviceConfigurationRepository;

    public DeviceConfigurationInfo getDeviceConfiguration(String deviceId) {
        DeviceConfiguration deviceConfiguration = deviceConfigurationRepository.getDeviceConfigurationByDeviceId(deviceId);
        return new DeviceConfigurationInfo(deviceConfiguration);
    }

    public void updateTimeOut(String deviceId, long timeOut) {
        DeviceConfiguration deviceConfiguration = deviceConfigurationRepository.getDeviceConfigurationByDeviceId(deviceId);
        deviceConfiguration.setTimeOut(timeOut);
        deviceConfigurationRepository.save(deviceConfiguration);
    }

    public void updateParkingStatus(String deviceId, boolean isParked) {
        DeviceConfiguration deviceConfiguration = deviceConfigurationRepository.getDeviceConfigurationByDeviceId(deviceId);
        deviceConfiguration.setParked(isParked);
        deviceConfigurationRepository.save(deviceConfiguration);
    }
}

