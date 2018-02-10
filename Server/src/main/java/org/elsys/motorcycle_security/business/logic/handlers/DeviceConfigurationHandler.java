package org.elsys.motorcycle_security.business.logic.handlers;

import org.elsys.motorcycle_security.business.logic.exceptions.InvalidDeviceIdException;
import org.elsys.motorcycle_security.business.logic.exceptions.InvalidInputException;
import org.elsys.motorcycle_security.dto.DeviceConfigurationDto;
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
    public void updateTimeOut(DeviceConfigurationDto deviceConfigurationDto) {
        DeviceConfiguration deviceConfiguration = deviceConfigurationRepository.getDeviceConfigurationByDeviceId(deviceConfigurationDto.getDeviceId());
        if(deviceConfiguration == null) throw new InvalidDeviceIdException("Invalid device id");
        if(deviceConfigurationDto.getTimeOut() == 0) throw new InvalidInputException("Invalid input");
        deviceConfiguration.setTimeOut(deviceConfigurationDto.getTimeOut());
        deviceConfigurationRepository.save(deviceConfiguration);
    }

    @Override
    public void updateParkingStatus(DeviceConfigurationDto deviceConfigurationDto) {
        DeviceConfiguration deviceConfiguration = deviceConfigurationRepository.getDeviceConfigurationByDeviceId(deviceConfigurationDto.getDeviceId());
        if(deviceConfiguration == null) throw new InvalidDeviceIdException("Invalid device id");
        deviceConfiguration.setParked(deviceConfigurationDto.isParked());
        deviceConfigurationRepository.save(deviceConfiguration);
        DataTransmiter dataTransmiter = dataTransmiterRepository.getGpsCoordinatesByDeviceId(deviceConfigurationDto.getDeviceId());
        Device device = deviceRepository.getDeviceByDeviceId(deviceConfigurationDto.getDeviceId());
        device.setParkedX(dataTransmiter.getX());
        device.setParkedY(dataTransmiter.getY());
        deviceRepository.save(device);
    }

    @Override
    public void updateStolenStatus(DeviceConfigurationDto deviceConfigurationDto) {
        DeviceConfiguration deviceConfiguration = deviceConfigurationRepository.getDeviceConfigurationByDeviceId(deviceConfigurationDto.getDeviceId());
        if(deviceConfiguration == null) throw new InvalidDeviceIdException("Invalid device id");
        deviceConfiguration.setStolen(deviceConfigurationDto.isStolen());
        deviceConfigurationRepository.save(deviceConfiguration);
    }
}

