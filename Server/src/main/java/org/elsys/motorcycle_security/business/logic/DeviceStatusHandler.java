package org.elsys.motorcycle_security.business.logic;

import org.elsys.motorcycle_security.models.DeviceStatus;
import org.elsys.motorcycle_security.repository.DeviceStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeviceStatusHandler {

    @Autowired
    private DeviceStatusRepository DeviceStatusRepository;

    public void createDeviceStatus(Long deviceid){
        DeviceStatus d = new DeviceStatus();
        d.setDeviceid(deviceid);
        d.setParked(false);
        d.setTimeout(5000);
        DeviceStatusRepository.save(d);
    }
}
