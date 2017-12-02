package org.elsys.motorcycle_security.controllers;

import org.elsys.motorcycle_security.business.logic.GPSCordsHandler;
import org.elsys.motorcycle_security.business.logic.UsersHandler;
import org.elsys.motorcycle_security.business.logic.DevicesHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class httpController {
    @Autowired
    GPSCordsHandler  gpsCordsHandler;
    @RequestMapping(value="/post/gpscordinates",method=GET)
    public void  request(@RequestParam(value="x", defaultValue="0") Long x,@RequestParam(value="y", defaultValue="0") Long y) {
        gpsCordsHandler.UpdateGPSCords((long)1,x,y);
    }
    @Autowired
    UsersHandler UsersHandler;
    @RequestMapping(value="/post/users",method=GET)
    public void  request(@RequestParam(value="username", defaultValue="") String username, @RequestParam(value="email", defaultValue="") String email, @RequestParam(value="password", defaultValue="") String password, @RequestParam(value="deviceid", defaultValue="0") long deviceid) {
        UsersHandler.UpdateUsers(username,password,email,deviceid);
    }
    @Autowired
    DevicesHandler DevicesHandler;
    @RequestMapping(value="/post/devices",method=GET)
    public void  request(@RequestParam(value="userid", defaultValue="") long userid) {
        DevicesHandler.UpdateDevices(userid);
    }
}