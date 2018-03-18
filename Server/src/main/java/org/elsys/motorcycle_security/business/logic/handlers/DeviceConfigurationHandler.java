package org.elsys.motorcycle_security.business.logic.handlers;

import org.elsys.motorcycle_security.business.logic.exceptions.InvalidDeviceIdException;
import org.elsys.motorcycle_security.business.logic.exceptions.InvalidInputException;
import org.elsys.motorcycle_security.business.logic.exceptions.UserDoesNotOwnDeviceException;
import org.elsys.motorcycle_security.dto.DeviceConfigurationDto;
import org.elsys.motorcycle_security.info.DeviceConfigurationInfo;
import org.elsys.motorcycle_security.models.DataTransmitter;
import org.elsys.motorcycle_security.models.Device;
import org.elsys.motorcycle_security.models.DeviceConfiguration;
import org.elsys.motorcycle_security.repository.DataTransmitterRepository;
import org.elsys.motorcycle_security.repository.DeviceConfigurationRepository;
import org.elsys.motorcycle_security.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeviceConfigurationHandler extends AbstractHandler implements org.elsys.motorcycle_security.business.logic.DeviceConfiguration {
    @Autowired
    private DeviceConfigurationRepository deviceConfigurationRepository;
    @Autowired
    private DataTransmitterRepository dataTransmitterRepository;
    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public DeviceConfigurationInfo getDeviceConfiguration(String deviceId) {
        DeviceConfiguration deviceConfiguration = deviceConfigurationRepository.getDeviceConfigurationByDeviceId(deviceId);
        if(deviceConfiguration == null) throw new InvalidDeviceIdException("Invalid device id");
        if(!checkUserOwnsDevice(new DeviceConfigurationDto(deviceId))) throw new UserDoesNotOwnDeviceException("This user doesn't own the specified device");
        return new DeviceConfigurationInfo(deviceConfiguration);
    }

    @Override
    public void updateTimeOut(DeviceConfigurationDto deviceConfigurationDto) {
        DeviceConfiguration deviceConfiguration = deviceConfigurationRepository.getDeviceConfigurationByDeviceId(deviceConfigurationDto.getDeviceId());
        if(deviceConfiguration == null) throw new InvalidDeviceIdException("Invalid device id");
        if(deviceConfigurationDto.getTimeOut() == 0) throw new InvalidInputException("Invalid input");
        if(!checkUserOwnsDevice(deviceConfigurationDto)) throw new UserDoesNotOwnDeviceException("This user doesn't own the specified device");
        deviceConfiguration.setTimeOut(deviceConfigurationDto.getTimeOut());
        deviceConfigurationRepository.save(deviceConfiguration);
    }

    @Override
    public void updateParkingStatus(DeviceConfigurationDto deviceConfigurationDto) {

        DeviceConfiguration deviceConfiguration = deviceConfigurationRepository.getDeviceConfigurationByDeviceId(deviceConfigurationDto.getDeviceId());
        if(deviceConfiguration == null) throw new InvalidDeviceIdException("Invalid device id");
        if(!checkUserOwnsDevice(deviceConfigurationDto)) throw new UserDoesNotOwnDeviceException("This user doesn't own the specified device");
        deviceConfiguration.setParked(deviceConfigurationDto.isParked());
        deviceConfigurationRepository.save(deviceConfiguration);
        DataTransmitter dataTransmitter = dataTransmitterRepository.getGpsCoordinatesByDeviceId(deviceConfigurationDto.getDeviceId());
        Device device = deviceRepository.getDeviceByDeviceId(deviceConfigurationDto.getDeviceId());
        device.setParkedX(dataTransmitter.getX());
        device.setParkedY(dataTransmitter.getY());
        deviceRepository.save(device);
    }

    @Override
    public void updateStolenStatus(DeviceConfigurationDto deviceConfigurationDto)  {
        DeviceConfiguration deviceConfiguration = deviceConfigurationRepository.getDeviceConfigurationByDeviceId(deviceConfigurationDto.getDeviceId());
        if(deviceConfiguration == null) throw new InvalidDeviceIdException("Invalid device id");
        if(!checkUserOwnsDevice(deviceConfigurationDto)) throw new UserDoesNotOwnDeviceException("This user doesn't own the specified device");
        deviceConfiguration.setStolen(deviceConfigurationDto.isStolen());
        deviceConfigurationRepository.save(deviceConfiguration);
    }
}

