package org.elsys.motorcycle_security.business.logic.handlers;

import org.elsys.motorcycle_security.business.logic.exceptions.InvalidDeviceIdException;
import org.elsys.motorcycle_security.business.logic.exceptions.InvalidEmailException;
import org.elsys.motorcycle_security.business.logic.exceptions.InvalidInputException;
import org.elsys.motorcycle_security.dto.DeviceDto;
import org.elsys.motorcycle_security.dto.UserDto;
import org.elsys.motorcycle_security.info.UserInfo;
import org.elsys.motorcycle_security.models.Device;
import org.elsys.motorcycle_security.models.DeviceConfiguration;
import org.elsys.motorcycle_security.models.DevicePin;
import org.elsys.motorcycle_security.models.User;
import org.elsys.motorcycle_security.repository.DeviceConfigurationRepository;
import org.elsys.motorcycle_security.repository.DevicePinRepository;
import org.elsys.motorcycle_security.repository.DeviceRepository;
import org.elsys.motorcycle_security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Component
public class UserHandler implements org.elsys.motorcycle_security.business.logic.User,UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private DeviceConfigurationRepository deviceConfigurationRepository;
    @Autowired
    private DevicePinRepository devicePinRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void createNewUser(UserDto userDto){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        long violations = validator.validate(userDto).size();
        if(violations>0) throw new InvalidInputException("Invalid input");
        User checkUser = userRepository.getUserAccountByEmail(userDto.getEmail());
        if(checkUser == null) {
            DevicePin devicePin = devicePinRepository.getPinByDeviceId(userDto.getDevices().get(0).getDeviceId());
            if (devicePin == null) throw new InvalidDeviceIdException("Invalid deviceId");
            User user = new User();
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setEmail(userDto.getEmail());
            userRepository.save(user);
            for (DeviceDto userDeviceDto : userDto.getDevices()) {
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
        else throw new InvalidEmailException("Account with specified email already exists.");
    }

    @Override
    public UserInfo getUser(String email) {
        User user = userRepository.getUserAccountByEmail(email);
        if(user == null) throw new InvalidEmailException("Invalid email");
        return new UserInfo(user);
    }

    @Override
    public void updatePassword(String email, String newPassword) {
        User user = userRepository.getUserAccountByEmail(email);
        if(user == null) throw new InvalidEmailException("Invalid user");
        if(newPassword.length() == 0) throw new InvalidInputException("Invalid input");
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserAccountByEmail(username);
        if(user == null) throw new UsernameNotFoundException("Username not found");
        return user;
    }
}
