package org.elsys.motorcycle_security.controllers;

import org.elsys.motorcycle_security.business.logic.GPSCordsHandler;
import org.elsys.motorcycle_security.business.logic.UsersHandler;
import org.elsys.motorcycle_security.business.logic.DevicesHandler;
import org.elsys.motorcycle_security.models.DataTransmiter;
import org.elsys.motorcycle_security.models.Users;
import org.elsys.motorcycle_security.models.Devices;
import org.elsys.motorcycle_security.repository.DataTransmiterRepository;
import org.elsys.motorcycle_security.repository.UsersRepository;
import org.elsys.motorcycle_security.repository.DevicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class httpController {
    //Arduino
    @Autowired
    private GPSCordsHandler  gpsCordsHandler;
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

    @RequestMapping(value="/device/send/gps-cordinates",method=POST)
    public void  request(@RequestParam(value="x", defaultValue="0") Long x,@RequestParam(value="y", defaultValue="0") Long y) {
        gpsCordsHandler.UpdateGPSCords((long)1,x,y);
    }

    @RequestMapping(value="/device/receive/{deviceid}/gps-cordinates",method=GET)
    @ResponseBody
    public List<DataTransmiter> request(@PathVariable (value="deviceid") Long deviceid) {
        return dataTransmiterRepository.findByDeviceid(deviceid);
    }

    @RequestMapping(value="/post/users",method=POST)
    public void  request(@RequestParam(value="username", defaultValue="") String username, @RequestParam(value="email", defaultValue="") String email, @RequestParam(value="password", defaultValue="") String password, @RequestParam(value="deviceid", defaultValue="0") long deviceid) {
        UsersHandler.UpdateUsers(username,password,email,deviceid);
    }
    @RequestMapping(value="/post/devices",method=POST)
    public void  request(@RequestParam(value="userid", defaultValue="") long userid) {
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