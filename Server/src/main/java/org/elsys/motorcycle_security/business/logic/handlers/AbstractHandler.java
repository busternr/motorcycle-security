package org.elsys.motorcycle_security.business.logic.handlers;

import org.elsys.motorcycle_security.dto.AbstractDto;
import org.elsys.motorcycle_security.models.User;
import org.elsys.motorcycle_security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class AbstractHandler {
    @Autowired
    private UserRepository userRepository;

    public boolean checkUserOwnsDevice(AbstractDto dto) {
        String email= SecurityContextHolder.getContext().getAuthentication().getName();
        String deviceId=dto.getDeviceId();
        User user = userRepository.getUserOwnsDevice(deviceId, email);
        if(user == null) return false;
        else return true;
    }
}
