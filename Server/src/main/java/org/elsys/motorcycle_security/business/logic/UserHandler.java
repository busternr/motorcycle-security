package org.elsys.motorcycle_security.business.logic;

import org.elsys.motorcycle_security.dto.UserInfo;
import org.elsys.motorcycle_security.models.Devices;
import org.elsys.motorcycle_security.models.User;
import org.elsys.motorcycle_security.repository.DevicesRepository;
import org.elsys.motorcycle_security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserHandler {
    @Autowired
    private UserRepository UserRepository;
    @Autowired
    private DevicesRepository DevicesRepository;

    public void createNewUser(String username, String password, String email, String deviceid){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        Devices device = new Devices();
        device.setUser(user);
        device.setDeviceId(deviceid);
        user.getUserDevices().add(device);
        UserRepository.save(user);
        DevicesRepository.save(device);
    }

    public UserInfo getUser(String userName) {
        User user = UserRepository.getUserAccountByUsername(userName);
        return new UserInfo(user);
    }
}
