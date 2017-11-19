package org.elsys.motorcycle_security.controllers;


import java.util.concurrent.atomic.AtomicLong;

import org.elsys.motorcycle_security.SendGPSCords;
import org.elsys.motorcycle_security.business.logic.GPSCordsHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class SendGPSCordsController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    GPSCordsHandler  gpsCordsHandler;

    @RequestMapping(value="/gps",method=GET)
    public SendGPSCords SendGPSCords(@RequestParam(value="x", defaultValue="34567") Double x) {
        GPSCordsHandler j = new GPSCordsHandler();
        return j.getLatestCordsForUser(106);
    }

    @RequestMapping(value="/test",method=GET)
    public void  test(@RequestParam(value="x", defaultValue="34567") Long x,@RequestParam(value="y", defaultValue="34567") Long y) {
        gpsCordsHandler.testCreate(x,y);
    }


}