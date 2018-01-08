package org.elsys.motorcycle_security.business.logic.handlers;

import org.elsys.motorcycle_security.business.logic.exceptions.InvalidEmailException;
import org.elsys.motorcycle_security.business.logic.exceptions.InvalidInputException;
import org.elsys.motorcycle_security.dto.DeviceDto;
import org.elsys.motorcycle_security.dto.UserDto;
import org.elsys.motorcycle_security.dto.UserInfo;
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
public class UserHandler implements org.elsys.motorcycle_security.business.logic.User {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private DeviceConfigurationRepository deviceConfigurationRepository;

    @Override
    public void createNewUser(UserDto userDto){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        long violations = validator.validate(userDto).size();
        if(violations>0) throw new InvalidInputException("Invalid input");
        User user = new User();
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        userRepository.save(user);
        for(DeviceDto userDeviceDto: userDto.getDevices()){
            Device device = new Device();
            device.setUser(user);
            device.setDeviceId(userDeviceDto.getDeviceId());
            deviceRepository.save(device);
            user.getUserDevices().add(device);
            DeviceConfiguration deviceConfiguration = new DeviceConfiguration();
            deviceConfiguration.setDevice(device);
            deviceConfigurationRepository.save(deviceConfiguration);
        }
    }

    @Override
    public UserInfo getUser(String email) {
        User user = userRepository.getUserAccountByEmail(email);
        if(user == null) throw new InvalidEmailException("Invalid email");
        return new UserInfo(user);
    }
}