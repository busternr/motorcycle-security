package org.elsys.motorcycle_security.business.logic;

import org.elsys.motorcycle_security.models.Devices;
import org.elsys.motorcycle_security.models.Users;
import org.elsys.motorcycle_security.repository.DevicesRepository;
import org.elsys.motorcycle_security.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsersHandler {
    @Autowired
    private UsersRepository UsersRepository;
    @Autowired
    private DevicesRepository DevicesRepository;

    public void createNewUser(String username, String password, String email, String deviceid){
        Users user = new Users();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        Devices device = new Devices();
        device.setUser(user);
        device.setDeviceId(deviceid);
        user.getUserDevices().add(device);
        UsersRepository.save(user);
        DevicesRepository.save(device);
    }
}
