package org.elsys.motorcycle_security.business.logic;

import org.elsys.motorcycle_security.models.Device;
import org.elsys.motorcycle_security.models.DeviceConfiguration;
import org.elsys.motorcycle_security.models.User;
import org.elsys.motorcycle_security.repository.DeviceConfigurationRepository;
import org.elsys.motorcycle_security.repository.DeviceRepository;
import org.elsys.motorcycle_security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeviceHandler {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private DeviceConfigurationRepository deviceConfigurationRepository;

    public void createNewDevice(long userId, String deviceid){
        Device device = new Device();
        User user = userRepository.getUserAccountById(userId);
        device.setUser(user);
        device.setDeviceId(deviceid);
        user.getUserDevices().add(device);
        DeviceConfiguration deviceConfiguration = new DeviceConfiguration();
        deviceConfiguration.setDevice(device);
        userRepository.save(user);
        deviceRepository.save(device);
        deviceConfigurationRepository.save(deviceConfiguration);
    }
}
