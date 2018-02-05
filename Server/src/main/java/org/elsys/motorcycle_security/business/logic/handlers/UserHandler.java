package org.elsys.motorcycle_security.business.logic.handlers;

import org.elsys.motorcycle_security.business.logic.exceptions.InvalidEmailException;
import org.elsys.motorcycle_security.business.logic.exceptions.InvalidInputException;
import org.elsys.motorcycle_security.business.logic.exceptions.InvalidUserIdException;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.security.InvalidParameterException;

@Component
public class UserHandler implements org.elsys.motorcycle_security.business.logic.User,UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private DeviceConfigurationRepository deviceConfigurationRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void createNewUser(UserDto userDto){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        long violations = validator.validate(userDto).size();
        if(violations>0) throw new InvalidInputException("Invalid input");
        User user = new User();
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
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
        UserInfo userInfo= new UserInfo(user);
        return userInfo;
    }

    @Override
    public void updatePassword(long userId, String oldPassword, String newPassword) {
        User user = userRepository.getUserAccountById(userId);
        if(user == null) throw new InvalidUserIdException("Invalid user id");
        if(newPassword.length() == 0) throw new InvalidInputException("Invalid input");
        if(oldPassword.matches(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        }
        else throw new InvalidInputException("Invalid old password");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserAccountByEmail(username);
        if(user==null)
            throw new UsernameNotFoundException("Username not found");

        return user;
    }
}
