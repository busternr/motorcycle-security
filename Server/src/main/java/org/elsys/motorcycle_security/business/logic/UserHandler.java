package org.elsys.motorcycle_security.business.logic;

import org.elsys.motorcycle_security.dto.UserInfo;
import org.elsys.motorcycle_security.models.Device;
import org.elsys.motorcycle_security.models.User;
import org.elsys.motorcycle_security.repository.DeviceRepository;
import org.elsys.motorcycle_security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserHandler {
    @Autowired
    private UserRepository UserRepository;
    @Autowired
    private DeviceRepository DeviceRepository;

    public void createNewUser(String username, String password, String email, String deviceid){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        Device device = new Device();
        device.setUser(user);
        device.setDeviceId(deviceid);
        user.getUserDevices().add(device);
        UserRepository.save(user);
        DeviceRepository.save(device);
    }

    public UserInfo getUser(String userName) {
        User user = UserRepository.getUserAccountByUsername(userName);
        return new UserInfo(user);
    }
}
