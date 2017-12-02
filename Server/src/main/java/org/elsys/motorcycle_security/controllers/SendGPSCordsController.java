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
    @Autowired
    GPSCordsHandler  gpsCordsHandler;
    @RequestMapping(value="/updategps",method=GET)
    public void  test(@RequestParam(value="x", defaultValue="34567") Long x,@RequestParam(value="y", defaultValue="0") Long y) {
        gpsCordsHandler.UpdateGPSCords((long)1,x,y);
    }
}