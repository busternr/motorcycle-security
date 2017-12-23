package org.elsys.motorcycle_security.business.logic;

import com.mysql.jdbc.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
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
public class UserHandler {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private DeviceConfigurationRepository deviceConfigurationRepository;



    public void createNewUser(UserDto userDto){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        long violations = validator.validate(userDto).size();
        if(violations>0)
            throw new InvalidParameterException("Input is missing or contains invalid field data "+validator.validate(userDto).iterator().next().getConstraintDescriptor().getMessageTemplate());
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

    public UserInfo getUser(String email) {
        User user = userRepository.getUserAccountByEmail(email);
        return new UserInfo(user);
    }
}
