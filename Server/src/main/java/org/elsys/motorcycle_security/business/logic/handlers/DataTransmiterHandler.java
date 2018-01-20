package org.elsys.motorcycle_security.business.logic.handlers;

import org.elsys.motorcycle_security.business.logic.exceptions.InvalidDeviceIdException;
import org.elsys.motorcycle_security.business.logic.exceptions.InvalidInputException;
import org.elsys.motorcycle_security.dto.DataTransmiterInfo;
import org.elsys.motorcycle_security.models.DataTransmiter;
import org.elsys.motorcycle_security.models.Device;
import org.elsys.motorcycle_security.repository.DataTransmiterRepository;
import org.elsys.motorcycle_security.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataTransmiterHandler implements org.elsys.motorcycle_security.business.logic.DataTransmiter {
    @Autowired
    private DataTransmiterRepository dataTransmiterRepository;
    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public void updateGPSCordinates(String deviceId, double x, double y) {
        Device device = deviceRepository.getDeviceByDeviceId(deviceId);
        if(device == null) throw new InvalidDeviceIdException("Invalid device id");
        if(x == 0 || y == 0) throw new InvalidInputException("Invalid input");
        DataTransmiter d = new DataTransmiter();
        d.setX(x);
        d.setY(y);
        Long time = System.currentTimeMillis();
        d.setTime(time);
        d.setDevice(device);
        dataTransmiterRepository.save(d);
    }

    @Override
    public DataTransmiterInfo getGPSCordinates(String deviceId) {
        DataTransmiter dataTransmiter = dataTransmiterRepository.getGpsCordinatesByDeviceId(deviceId);
        if(dataTransmiter == null) throw new InvalidDeviceIdException("Invalid device id");
        return new DataTransmiterInfo(dataTransmiter);
    }
}
