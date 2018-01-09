package org.elsys.motorcycle_security.business.logic.handlers;

import org.elsys.motorcycle_security.business.logic.exceptions.InvalidDeviceIdException;
import org.elsys.motorcycle_security.dto.DeviceConfigurationInfo;
import org.elsys.motorcycle_security.models.DataTransmiter;
import org.elsys.motorcycle_security.models.Device;
import org.elsys.motorcycle_security.models.DeviceConfiguration;
import org.elsys.motorcycle_security.repository.DataTransmiterRepository;
import org.elsys.motorcycle_security.repository.DeviceConfigurationRepository;
import org.elsys.motorcycle_security.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeviceConfigurationHandler implements org.elsys.motorcycle_security.business.logic.DeviceConfiguration {
    @Autowired
    private DeviceConfigurationRepository deviceConfigurationRepository;
    @Autowired
    private DataTransmiterRepository dataTransmiterRepository;
    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public DeviceConfigurationInfo getDeviceConfiguration(String deviceId) {
        DeviceConfiguration deviceConfiguration = deviceConfigurationRepository.getDeviceConfigurationByDeviceId(deviceId);
        if(deviceConfiguration == null) throw new InvalidDeviceIdException("Invalid device id");
        return new DeviceConfigurationInfo(deviceConfiguration);

    }

    @Override
    public void updateTimeOut(String deviceId, long timeOut) {
        DeviceConfiguration deviceConfiguration = deviceConfigurationRepository.getDeviceConfigurationByDeviceId(deviceId);
        if(deviceConfiguration == null) throw new InvalidDeviceIdException("Invalid device id");
        deviceConfiguration.setTimeOut(timeOut);
        deviceConfigurationRepository.save(deviceConfiguration);
    }

    @Override
    public void updateParkingStatus(String deviceId, boolean isParked) {
        DeviceConfiguration deviceConfiguration = deviceConfigurationRepository.getDeviceConfigurationByDeviceId(deviceId);
        if(deviceConfiguration == null) throw new InvalidDeviceIdException("Invalid device id");
        deviceConfiguration.setParked(isParked);
        deviceConfigurationRepository.save(deviceConfiguration);
        DataTransmiter dataTransmiter = dataTransmiterRepository.getGpsCordinatesByDeviceId(deviceId);
        Device device = deviceRepository.getDeviceByDeviceId(deviceId);
        device.setParkedX(dataTransmiter.getX());
        device.setParkedY(dataTransmiter.getY());
        deviceRepository.save(device);
    }
}

