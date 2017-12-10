package org.elsys.motorcycle_security.controllers;

import org.elsys.motorcycle_security.business.logic.UserHandler;
import org.elsys.motorcycle_security.business.logic.DevicesHandler;
import org.elsys.motorcycle_security.dto.UserInfo;
import org.elsys.motorcycle_security.models.DataTransmiter;
import org.elsys.motorcycle_security.models.Devices;
import org.elsys.motorcycle_security.repository.DataTransmiterRepository;
import org.elsys.motorcycle_security.repository.DeviceConfigurationRepository;
import org.elsys.motorcycle_security.repository.UserRepository;
import org.elsys.motorcycle_security.repository.DevicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class ClientController {
    @Autowired
    private UserHandler usersHandler;
    @Autowired
    private DevicesHandler DevicesHandler;
    @Autowired
    private DataTransmiterRepository dataTransmiterRepository;
    @Autowired
    private DeviceConfigurationRepository DeviceConfigurationRepository;
    @Autowired
    private UserRepository UserRepository;
    @Autowired
    private DevicesRepository devicesRepository;

//    @RequestMapping(value = "/client/send/parking-status", method = PUT)
//    public void updateParkingStatusByDeviceId(@RequestParam(value = "deviceId", defaultValue = "0") long deviceId, @RequestParam(value = "isParked", defaultValue = "0") boolean isParked) {
//        DeviceSettingsRepository.updateParkingStatusByDeviceId(deviceId, isParked);
//    }
//
    /*@RequestMapping(value = "/client/send/timeout", method = PUT)
    public void updateTimeoutByDeviceId(@RequestParam(value = "deviceId", defaultValue = "0") long deviceId, @RequestParam(value = "timeout", defaultValue = "0") long timeout) {
        DeviceConfigurationRepository.updateTimeoutByDeviceId(deviceId, timeout);
    }*/

    @RequestMapping(value = "/client/send/create-new-user", method = POST)
    public void createNewUser(@RequestParam(value = "username", defaultValue = "") String username, @RequestParam(value = "email", defaultValue = "") String email, @RequestParam(value = "password", defaultValue = "") String password, @RequestParam(value = "deviceId", defaultValue = "") String deviceId) {
        usersHandler.createNewUser(username, password, email, deviceId);
    }
    @RequestMapping(value = "/client/receive/{deviceId}/gps-cordinates", method = GET)
    @ResponseBody
    public Object getGpsCordinatesBydeviceId(@PathVariable(value = "deviceId") long deviceId) {
        Devices device = devicesRepository.getDeviceById(deviceId);
        long devid = device.getId();
        List<DataTransmiter> list = dataTransmiterRepository.getGpsCordinatesByDeviceId(devid);
        return list.get(list.size() - 1);
    }
    @RequestMapping(value="/client/receive/user-account",method=GET)
    @ResponseBody
    public UserInfo getUserAccountByUsername(@RequestHeader("userName") String userName) {
        UserInfo userInfo = usersHandler.getUser(userName);
        return userInfo;
    }
}


