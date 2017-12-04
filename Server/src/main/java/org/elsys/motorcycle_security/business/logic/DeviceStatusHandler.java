package org.elsys.motorcycle_security.business.logic;

import org.elsys.motorcycle_security.repository.DeviceStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeviceStatusHandler {

    @Autowired
    private DeviceStatusRepository DeviceStatusRepository;

    public void updateParkingStatus(long deviceid, boolean isParked){
        DeviceStatusRepository.updateParkingStatus(deviceid, isParked);
    }
}
