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
    public DeviceConfigurationInfo getDeviceConfigurationForClient(String deviceId) {
        DeviceConfiguration deviceConfiguration = deviceConfigurationRepository.getDeviceConfigurationByDeviceId(deviceId);
        if(deviceConfiguration == null) throw new InvalidDeviceIdException("Invalid device id");
        if(!checkUserOwnsDevice(new DeviceConfigurationDto(deviceId))) throw new UserDoesNotOwnDeviceException("This user doesn't own the specified device");
        writeLog("Received device configuration for device:" + deviceId, true);
        return new DeviceConfigurationInfo(deviceConfiguration);
    }

    @Override
    public DeviceConfigurationInfo getDeviceConfigurationForDevice(String deviceId) {
        DeviceConfiguration deviceConfiguration = deviceConfigurationRepository.getDeviceConfigurationByDeviceId(deviceId);
        if(deviceConfiguration == null) throw new InvalidDeviceIdException("Invalid device id");
        writeLog("Received device configuration for device:" + deviceId, false);
        return new DeviceConfigurationInfo(deviceConfiguration);
    }

    @Override
    public void updateTimeOut(DeviceConfigurationDto deviceConfigurationDto) {
        DeviceConfiguration deviceConfiguration = deviceConfigurationRepository.getDeviceConfigurationByDeviceId(deviceConfigurationDto.getDeviceId());
        if(deviceConfiguration == null) throw new InvalidDeviceIdException("Invalid device id");
        if(deviceConfigurationDto.getTimeOut() == 0) throw new InvalidInputException("Invalid input");
        if(!checkUserOwnsDevice(deviceConfigurationDto)) throw new UserDoesNotOwnDeviceException("This user doesn't own the specified device");
        deviceConfiguration.setTimeOut(deviceConfigurationDto.getTimeOut());
        writeLog("Updated device configuration for device:" + deviceConfigurationDto.getDeviceId(), true);
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
        writeLog("Updated parking status for device:" + deviceConfigurationDto.getDeviceId(), true);
        deviceRepository.save(device);
    }

    @Override
    public void updateStolenStatus(DeviceConfigurationDto deviceConfigurationDto)  {
        DeviceConfiguration deviceConfiguration = deviceConfigurationRepository.getDeviceConfigurationByDeviceId(deviceConfigurationDto.getDeviceId());
        if(deviceConfiguration == null) throw new InvalidDeviceIdException("Invalid device id");
        if(!checkUserOwnsDevice(deviceConfigurationDto)) throw new UserDoesNotOwnDeviceException("This user doesn't own the specified device");
        deviceConfiguration.setStolen(deviceConfigurationDto.isStolen());
        writeLog("Updated stolen status for device:" + deviceConfigurationDto.getDeviceId(), true);
        deviceConfigurationRepository.save(deviceConfiguration);
    }
}

