package org.elsys.motorcycle_security.controllers;

import org.elsys.motorcycle_security.business.logic.DataTransmiterHandler;
import org.elsys.motorcycle_security.business.logic.DeviceConfigurationHandler;
import org.elsys.motorcycle_security.dto.DeviceConfigurationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class DeviceController {
    @Autowired
    private DataTransmiterHandler dataTransmiterHandler;
    @Autowired
    private DeviceConfigurationHandler deviceConfigurationHandler;

    @RequestMapping(value="/device/send/gps-cordinates",method=POST)
    public void  sendGpsCordinates(@RequestParam(value="deviceId") String deviceId,
                                   @RequestParam(value="x", defaultValue="0") long x,
                                   @RequestParam(value="y", defaultValue="0") long y) {
        dataTransmiterHandler.UpdateGPSCordinates(deviceId,x,y);
    }

    @RequestMapping(value="/device/{deviceId}/receive/device-configuration",method=GET)
    @ResponseBody
    public DeviceConfigurationInfo getDeviceConfigurationDeviceId(@PathVariable (value="deviceId") String deviceId) {
        DeviceConfigurationInfo deviceConfigurationInfo = deviceConfigurationHandler.getDeviceConfiguration(deviceId);
        return deviceConfigurationInfo;
    }
}