package org.elsys.motorcycle_security.controllers;

import org.elsys.motorcycle_security.business.logic.DataTransmiter;
import org.elsys.motorcycle_security.business.logic.DeviceConfiguration;
import org.elsys.motorcycle_security.business.logic.exceptions.InvalidDeviceIdException;
import org.elsys.motorcycle_security.business.logic.exceptions.InvalidInputException;
import org.elsys.motorcycle_security.dto.DeviceConfigurationInfo;
import org.elsys.motorcycle_security.dto.ErrorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class DeviceController {
    @Autowired
    private DataTransmiter dataTransmiterHandler;
    @Autowired
    private DeviceConfiguration deviceConfigurationHandler;

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public static ResponseEntity handleTypeMismatch() {
        return new ResponseEntity(new ErrorDto("Invalid input"), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/device/send/gps-cordinates",method=POST)
    public ResponseEntity sendGpsCordinates(@RequestParam(value="deviceId") String deviceId,
                                            @RequestParam(value="x", defaultValue="0") double x,
                                            @RequestParam(value="y", defaultValue="0") double y) {
        try {
            dataTransmiterHandler.updateGPSCordinates(deviceId, x, y);
        }
        catch(InvalidDeviceIdException exception) {
            return new ResponseEntity(new ErrorDto(exception),HttpStatus.BAD_REQUEST);
        }
        catch(InvalidInputException exception) {
            return new ResponseEntity(new ErrorDto(exception),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value="/device/{deviceId}/receive/device-configuration",method=GET)
    @ResponseBody
    public ResponseEntity getDeviceConfigurationDeviceId(@PathVariable (value="deviceId") String deviceId) {
        DeviceConfigurationInfo deviceConfigurationInfo;
        try {
            deviceConfigurationInfo = deviceConfigurationHandler.getDeviceConfiguration(deviceId);
        }
        catch(InvalidDeviceIdException exception) {
            return new ResponseEntity(new ErrorDto(exception),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(deviceConfigurationInfo,HttpStatus.OK);
    }
}