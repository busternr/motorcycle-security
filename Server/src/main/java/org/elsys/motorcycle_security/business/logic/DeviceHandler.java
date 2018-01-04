package org.elsys.motorcycle_security.business.logic;

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
public class DeviceHandler {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private DeviceConfigurationRepository deviceConfigurationRepository;

    public void createNewDevice(DeviceDto deviceDto){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        long violations = validator.validate(deviceDto).size();
        if(violations>0) throw new InvalidParameterException("Input is missing or contains invalid field data "+validator.validate(deviceDto).iterator().next().getConstraintDescriptor().getMessageTemplate());

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

    public DeviceInfo getDevice(String deviceId) {
        Device device = deviceRepository.getDeviceByDeviceId(deviceId);
        return new DeviceInfo(device);
    }

    public void updateUpTime(String deviceId, long upTime) {
        Device device = deviceRepository.getDeviceByDeviceId(deviceId);
        device.setUpTime(upTime);
        deviceRepository.save(device);
    }
}
