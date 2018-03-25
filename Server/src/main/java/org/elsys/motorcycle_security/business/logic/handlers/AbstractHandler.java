package org.elsys.motorcycle_security.business.logic.handlers;

import org.elsys.motorcycle_security.dto.AbstractDto;
import org.elsys.motorcycle_security.models.Device;
import org.elsys.motorcycle_security.models.User;
import org.elsys.motorcycle_security.repository.DeviceRepository;
import org.elsys.motorcycle_security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class AbstractHandler {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DeviceRepository deviceRepository;

    public boolean checkUserOwnsDevice(AbstractDto abstractDto) {
        boolean found = false;
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        String deviceId = abstractDto.getDeviceId();
        User user = userRepository.getUserAccountByEmail(email);
        Device device = deviceRepository.getDeviceByDeviceId(deviceId);
        for(Device checkDevice : user.getUserDevices()) {
            if(device.getDeviceId().equals(checkDevice.getDeviceId())) found = true;
        }
        return found;
    }
}
