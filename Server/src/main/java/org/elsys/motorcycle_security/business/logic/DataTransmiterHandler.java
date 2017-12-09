package org.elsys.motorcycle_security.business.logic;

import org.elsys.motorcycle_security.models.DataTransmiter;
import org.elsys.motorcycle_security.models.Devices;
import org.elsys.motorcycle_security.repository.DataTransmiterRepository;
import org.elsys.motorcycle_security.repository.DevicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DataTransmiterHandler {
    @Autowired
    private DataTransmiterRepository dataTransmiterRepository;
    @Autowired
    private DevicesRepository devicesRepository;

    public void UpdateGPSCords(final String deviceId,Long x,Long y){
        DataTransmiter d = new DataTransmiter();
        d.setX(x);
        d.setY(y);
        Long Date = System.currentTimeMillis();
        d.setTime(Date);
        Devices device = devicesRepository.getDeviceByDeviceId(deviceId);
        d.setDevice(device);
        dataTransmiterRepository.save(d);
    }
}
