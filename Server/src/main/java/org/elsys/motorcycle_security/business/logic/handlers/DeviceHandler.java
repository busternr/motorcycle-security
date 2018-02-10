package org.elsys.motorcycle_security.business.logic.handlers;

import org.elsys.motorcycle_security.business.logic.exceptions.InvalidDeviceIdException;
import org.elsys.motorcycle_security.business.logic.exceptions.InvalidInputException;
import org.elsys.motorcycle_security.dto.DeviceDto;
import org.elsys.motorcycle_security.dto.DeviceInfo;
import org.elsys.motorcycle_security.models.Device;
import org.elsys.motorcycle_security.models.DeviceConfiguration;
import org.elsys.motorcycle_security.models.User;
import org.elsys.motorcycle_security.repository.DeviceConfigurationRepository;
import org.elsys.motorcycle_security.repository.DeviceRepository;
import org.elsys.motorcycle_security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.security.InvalidParameterException;

@Component
public class DeviceHandler implements org.elsys.motorcycle_security.business.logic.Device {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private DeviceConfigurationRepository deviceConfigurationRepository;

    @Override
    public void createNewDevice(DeviceDto deviceDto){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        long violations = validator.validate(deviceDto).size();
        if(violations>0) throw new InvalidInputException("Invalid input");
        Device device = new Device();
        User user = userRepository.getUserAccountById(deviceDto.getUserId());
        device.setUser(user);
        device.setDeviceId(deviceDto.getDeviceId());
        user.getUserDevices().add(device);
        DeviceConfiguration deviceConfiguration = new DeviceConfiguration();
        deviceConfiguration.setDevice(device);
        userRepository.save(user);
        deviceRepository.save(device);
        deviceConfigurationRepository.save(deviceConfiguration);
    }

    @Override
    public DeviceInfo getDevice(String deviceId) {
        Device device = deviceRepository.getDeviceByDeviceId(deviceId);
        if(device == null) throw new InvalidDeviceIdException("Invalid device id");
        return new DeviceInfo(device);
    }

    @Override
    public void updateParkedCoordinates(DeviceDto deviceDto) {
        Device device = deviceRepository.getDeviceByDeviceId(deviceDto.getDeviceId());
        if(device == null) throw new InvalidDeviceIdException("Invalid device id");
        if(deviceDto.getParkedX() == 0 || deviceDto.getParkedY() == 0) throw new InvalidInputException("Invalid input");
        device.setParkedX(deviceDto.getParkedX());
        device.setParkedY(deviceDto.getParkedY());
        deviceRepository.save(device);
    }
}
