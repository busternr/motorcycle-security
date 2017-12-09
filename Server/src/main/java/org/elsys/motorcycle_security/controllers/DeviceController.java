package org.elsys.motorcycle_security.controllers;

import org.elsys.motorcycle_security.business.logic.DataTransmiterHandler;
import org.elsys.motorcycle_security.repository.DeviceConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class DeviceController {
    @Autowired
    private DataTransmiterHandler dataTransmiterHandler;
    @Autowired
    private DeviceConfigurationRepository deviceConfigurationRepository;

    @RequestMapping(value="/device/send/gps-cordinates",method=POST)
    public void  sendGpsCordinates(@RequestParam(value="deviceId") String deviceId,
                                   @RequestParam(value="x", defaultValue="0") long x,
                                   @RequestParam(value="y", defaultValue="0") long y) {
        dataTransmiterHandler.UpdateGPSCords(deviceId,x,y);
    }
//    @RequestMapping(value="/device/receive/{deviceId}/timeout",method=GET)
//    @ResponseBody
//    public long getTimeoutBydeviceId(@PathVariable (value="deviceId") long deviceId) {
//        return DeviceSettingsRepository.getTimeoutByDeviceId(deviceId);
//    }
//    @RequestMapping(value="/device/receive/{deviceId}/parking-status",method=GET)
//    @ResponseBody
//    public boolean getParkingStatusBydeviceId(@PathVariable (value="deviceId") long deviceId) {
//        return DeviceSettingsRepository.getParkingStatusByDeviceId(deviceId);
//    }
}