package org.elsys.motorcycle_security.business.logic;

import org.elsys.motorcycle_security.models.Devices;
import org.elsys.motorcycle_security.repository.DevicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DevicesHandler {
    @Autowired
    private DevicesRepository DevicesRepository;

    public void createNewDevice(long userId){
        Devices d = new Devices();
        d.setUserId(userId);
        DevicesRepository.save(d);
    }
}
