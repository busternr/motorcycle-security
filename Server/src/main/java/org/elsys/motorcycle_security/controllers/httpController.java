package org.elsys.motorcycle_security.controllers;

import org.elsys.motorcycle_security.business.logic.DataTransmiterHandler;
import org.elsys.motorcycle_security.business.logic.UsersHandler;
import org.elsys.motorcycle_security.business.logic.DevicesHandler;
import org.elsys.motorcycle_security.business.logic.DeviceStatusHandler;
import org.elsys.motorcycle_security.models.DataTransmiter;
import org.elsys.motorcycle_security.models.DeviceStatus;
import org.elsys.motorcycle_security.models.Users;
import org.elsys.motorcycle_security.models.Devices;
import org.elsys.motorcycle_security.repository.DataTransmiterRepository;
import org.elsys.motorcycle_security.repository.UsersRepository;
import org.elsys.motorcycle_security.repository.DevicesRepository;
import org.elsys.motorcycle_security.repository.DeviceStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Objects;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import static sun.security.ssl.HandshakeMessage.debug;

@RestController
public class httpController {
    @Autowired
    private DataTransmiterHandler dataTransmiterHandler;
    @Autowired
    private UsersHandler UsersHandler;
    @Autowired
    private DevicesHandler DevicesHandler;
    @Autowired
    private DataTransmiterRepository dataTransmiterRepository;
    @Autowired
    private DevicesRepository DevicesRepository;
    @Autowired
    private UsersRepository UsersRepository;
    @Autowired
    private DeviceStatusRepository DeviceStatusRepository;
    @Autowired
    private DeviceStatusHandler  DeviceStatusHandler;



    @RequestMapping(value="/device/send/gps-cordinates",method=POST)
    public void  sendGpsCordinates(@RequestParam(value="deviceid", defaultValue="0") long deviceid,@RequestParam(value="x", defaultValue="0") long x,@RequestParam(value="y", defaultValue="0") long y) {
        dataTransmiterHandler.UpdateGPSCords(deviceid,x,y);
    }
    @RequestMapping(value="/device/receive/{deviceid}/timeout",method=GET)
    @ResponseBody
    public long getTimeoutByDeviceId(@PathVariable (value="deviceid") long deviceid) {
        return DeviceStatusRepository.getTimeoutByDeviceId(deviceid);
    }
    @RequestMapping(value="/device/receive/{deviceid}/parking-status",method=GET)
    @ResponseBody
    public boolean getParkingStatusByDeviceId(@PathVariable (value="deviceid") long deviceid) {
        return DeviceStatusRepository.getParkingStatusByDeviceId(deviceid);
    }

    @RequestMapping(value="/client/receive/{deviceid}/gps-cordinates",method=GET)
    @ResponseBody
    public Object getGpsCordinatesByDeviceId(@PathVariable (value="deviceid") long deviceid) {
        List<DataTransmiter> list = dataTransmiterRepository.getGpsCordinatesByDeviceId(deviceid);
        return list.get(list.size() - 1);
    }
    @RequestMapping(value="/client/send/parking-status",method=PUT)
    public void request(@RequestParam(value="deviceid", defaultValue="0") long deviceid,@RequestParam(value="isParked", defaultValue="0") boolean isParked) {
        DeviceStatusRepository.updateParkingStatusByDeviceId(deviceid,isParked);
    }
    @RequestMapping(value="/client/send/timeout",method=PUT)
    public void request(@RequestParam(value="deviceid", defaultValue="0") long deviceid,@RequestParam(value="timeout", defaultValue="0") long timeout) {
        DeviceStatusRepository.updateTimeoutByDeviceId(deviceid,timeout);
    }
    
    @RequestMapping(value="/post/users",method=POST)
    public void  request(@RequestParam(value="username", defaultValue="") String username, @RequestParam(value="email", defaultValue="") String email, @RequestParam(value="password", defaultValue="") String password, @RequestParam(value="deviceid", defaultValue="0") long deviceid) {
        UsersHandler.UpdateUsers(username,password,email,deviceid);
    }
    @RequestMapping(value="/post/devices",method=POST)
    public void  request2(@RequestParam(value="userid", defaultValue="") long userid) {
        DevicesHandler.UpdateDevices(userid);
    }



    @RequestMapping(value="/get/gpscordinates",method=GET)
    @ResponseBody
    public DataTransmiter getDataTransmiter(@RequestParam(value="deviceid", defaultValue="0") long deviceid) {
        return dataTransmiterRepository.findOne(deviceid);
    }

    @RequestMapping(value="/get/allgpscordinates",method=GET)
    public @ResponseBody Iterable<DataTransmiter> getAllDataTransmiters() {
        return dataTransmiterRepository.findAll();
    }
    @RequestMapping(value="/get/alldevices",method=GET)
    public @ResponseBody Iterable<Devices> getAllDevices() {
        return DevicesRepository.findAll();
    }

    @RequestMapping(value="/get/allusers",method=GET)
    public @ResponseBody Iterable<Users> getAllUsers() {
        return UsersRepository.findAll();
    }
}