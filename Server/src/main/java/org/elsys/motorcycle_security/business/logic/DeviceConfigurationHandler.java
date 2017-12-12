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

    public DeviceConfigurationInfo getDeviceConfigurationIsParked(long deviceId) {
        DeviceConfiguration deviceConfiguration = deviceConfigurationRepository.getDeviceConfigurationDeviceId(deviceId);
        return new DeviceConfigurationInfo(deviceConfiguration);
    }
}
