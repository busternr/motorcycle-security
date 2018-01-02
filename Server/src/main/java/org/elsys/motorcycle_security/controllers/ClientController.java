package org.elsys.motorcycle_security.controllers;

import org.elsys.motorcycle_security.business.logic.DataTransmiterHandler;
import org.elsys.motorcycle_security.business.logic.DeviceConfigurationHandler;
import org.elsys.motorcycle_security.business.logic.DeviceHandler;
import org.elsys.motorcycle_security.business.logic.UserHandler;
import org.elsys.motorcycle_security.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
public class ClientController {
    @Autowired
    private UserHandler userHandler;
    @Autowired
    private DeviceHandler deviceHandler;
    @Autowired
    private DataTransmiterHandler dataTransmiterHandler;
    @Autowired
    private DeviceConfigurationHandler deviceConfigurationHandler;

    @RequestMapping(value = "/client/send/parking-status", method = PUT)
    public void updateParkingStatusByDeviceId(@RequestParam(value = "deviceId", defaultValue = "0") String deviceId, @RequestParam(value = "isParked", defaultValue = "0") boolean isParked) {
        deviceConfigurationHandler.updateParkingStatus(deviceId, isParked);
    }

    @RequestMapping(value = "/client/send/timeout", method = PUT)
    public void updateTimeoutByDeviceId(@RequestParam(value = "deviceId", defaultValue = "0") String deviceId, @RequestParam(value = "timeout", defaultValue = "0") long timeOut) {
        deviceConfigurationHandler.updateTimeOut(deviceId, timeOut);
    }

    @RequestMapping(value = "/client/send/create-new-user", method = POST)
    public void createNewUser(@RequestBody UserDto newUser) {
        userHandler.createNewUser(newUser);
    }

    @RequestMapping(value = "/client/send/create-new-device", method = POST)
    public void createNewUser(@RequestBody DeviceDto newDevice) {
        deviceHandler.createNewDevice(newDevice);
    }

    @RequestMapping(value = "/client/{deviceId}/receive/gps-cordinates", method = GET)
    @ResponseBody
    public DataTransmiterInfo getGpsCordinatesBydeviceId(@PathVariable(value = "deviceId") String deviceId) {
        DataTransmiterInfo dataTransmiterInfo = dataTransmiterHandler.getGPSCordinates(deviceId);
        return dataTransmiterInfo;
    }

    @RequestMapping(value="/client/receive/user-account",method=GET)
    @ResponseBody
    public UserInfo getUserAccountByUsername(@RequestHeader("email") String email) {
        UserInfo userInfo = userHandler.getUser(email);
        return userInfo;
    }

    @RequestMapping(value="/client/receive/device",method=GET)
    @ResponseBody
    public DeviceInfo geDeviceByDeviceID(@RequestParam(value = "deviceId", defaultValue = "0") String deviceId) {
        DeviceInfo deviceInfo = deviceHandler.getDevice(deviceId);
        return deviceInfo;
    }
}


