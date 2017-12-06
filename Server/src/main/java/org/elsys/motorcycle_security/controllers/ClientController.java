package org.elsys.motorcycle_security.controllers;

import org.elsys.motorcycle_security.business.logic.UsersHandler;
import org.elsys.motorcycle_security.business.logic.DevicesHandler;
import org.elsys.motorcycle_security.models.DataTransmiter;
import org.elsys.motorcycle_security.models.Users;
import org.elsys.motorcycle_security.repository.DataTransmiterRepository;
import org.elsys.motorcycle_security.repository.DeviceSettingsRepository;
import org.elsys.motorcycle_security.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.OneToOne;
import java.util.List;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
public class ClientController {
    @Autowired
    private UsersHandler UsersHandler;
    @Autowired
    private DevicesHandler DevicesHandler;
    @Autowired
    private DataTransmiterRepository dataTransmiterRepository;
    @Autowired
    private DeviceSettingsRepository DeviceSettingsRepository;
    @Autowired
    private UsersRepository UsersRepository;

    @RequestMapping(value="/client/send/parking-status",method=PUT)
    public void updateParkingStatusByDeviceId(@RequestParam(value="deviceId", defaultValue="0") long deviceId,@RequestParam(value="isParked", defaultValue="0") boolean isParked) {
        DeviceSettingsRepository.updateParkingStatusByDeviceId(deviceId,isParked);
    }
    @RequestMapping(value="/client/send/timeout",method=PUT)
    public void updateTimeoutByDeviceId(@RequestParam(value="deviceId", defaultValue="0") long deviceId,@RequestParam(value="timeout", defaultValue="0") long timeout) {
        DeviceSettingsRepository.updateTimeoutByDeviceId(deviceId,timeout);
    }
    @RequestMapping(value="/client/send/create-new-user",method=POST)
    public void  createNewUser(@RequestParam(value="username", defaultValue="") String username, @RequestParam(value="email", defaultValue="") String email, @RequestParam(value="password", defaultValue="") String password, @RequestParam(value="deviceId", defaultValue="0") long deviceId) {
        UsersHandler.createNewUser(username,password,email,deviceId);
    }
    @RequestMapping(value="client/send/create-new-device",method=POST)
    public void  createNewDevice(@RequestParam(value="userid", defaultValue="") long userid) {
        DevicesHandler.createNewDevice(userid);
    }
    @RequestMapping(value="/client/receive/{deviceId}/gps-cordinates",method=GET)
    @ResponseBody
    public Object getGpsCordinatesBydeviceId(@PathVariable (value="deviceId") long deviceId) {
        List<DataTransmiter> list = dataTransmiterRepository.getGpsCordinatesByDeviceId(deviceId);
        return list.get(list.size() - 1);
    }
    @RequestMapping(value="/client/receive/{username}/user-account",method=GET)
    @ResponseBody
    public List<Users> getUserAccountByUsername(@PathVariable (value="username") String username) {
        List<Users> list = UsersRepository.getUserAccountByUsername(username);
        return list;
    }
}