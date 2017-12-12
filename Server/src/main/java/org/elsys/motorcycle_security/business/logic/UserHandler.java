package org.elsys.motorcycle_security.business.logic;

import org.elsys.motorcycle_security.dto.UserInfo;
import org.elsys.motorcycle_security.models.Device;
import org.elsys.motorcycle_security.models.DeviceConfiguration;
import org.elsys.motorcycle_security.models.User;
import org.elsys.motorcycle_security.repository.DeviceConfigurationRepository;
import org.elsys.motorcycle_security.repository.DeviceRepository;
import org.elsys.motorcycle_security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserHandler {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private DeviceConfigurationRepository deviceConfigurationRepository;

    public void createNewUser(String username, String password, String email, String deviceid){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        Device device = new Device();
        device.setUser(user);
        device.setDeviceId(deviceid);
        user.getUserDevices().add(device);
        DeviceConfiguration deviceConfiguration = new DeviceConfiguration();
        deviceConfiguration.setDevice(device);
        userRepository.save(user);
        deviceRepository.save(device);
        deviceConfigurationRepository.save(deviceConfiguration);
    }

    public UserInfo getUser(String userName) {
        User user = userRepository.getUserAccountByUsername(userName);
        return new UserInfo(user);
    }
}
