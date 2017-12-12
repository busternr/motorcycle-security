package org.elsys.motorcycle_security.business.logic;

import org.elsys.motorcycle_security.dto.DataTransmiterInfo;
import org.elsys.motorcycle_security.models.DataTransmiter;
import org.elsys.motorcycle_security.models.Device;
import org.elsys.motorcycle_security.repository.DataTransmiterRepository;
import org.elsys.motorcycle_security.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DataTransmiterHandler {
    @Autowired
    private DataTransmiterRepository dataTransmiterRepository;
    @Autowired
    private DeviceRepository deviceRepository;

    public void UpdateGPSCordinates(final String deviceId,Long x,Long y){
        DataTransmiter d = new DataTransmiter();
        d.setX(x);
        d.setY(y);
        Long Date = System.currentTimeMillis();
        d.setTime(Date);
        Device device = deviceRepository.getDeviceByDeviceId(deviceId);
        d.setDevice(device);
        dataTransmiterRepository.save(d);
    }

    public DataTransmiterInfo getGPSCordinates(long deviceId) {
        DataTransmiter dataTransmiter = dataTransmiterRepository.getGpsCordinatesByDeviceId(deviceId);
        return new DataTransmiterInfo(dataTransmiter);
    }
}
