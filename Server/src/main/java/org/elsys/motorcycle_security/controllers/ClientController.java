package org.elsys.motorcycle_security.controllers;

import org.elsys.motorcycle_security.business.logic.DataTransmiter;
import org.elsys.motorcycle_security.business.logic.DeviceConfiguration;
import org.elsys.motorcycle_security.business.logic.exceptions.InvalidDeviceIdException;
import org.elsys.motorcycle_security.business.logic.exceptions.InvalidEmailException;
import org.elsys.motorcycle_security.business.logic.exceptions.InvalidInputException;
import org.elsys.motorcycle_security.business.logic.handlers.DeviceHandler;
import org.elsys.motorcycle_security.business.logic.handlers.UserHandler;
import org.elsys.motorcycle_security.dto.*;
import org.elsys.motorcycle_security.repository.DataTransmiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
    private DataTransmiter dataTransmiterHandler;
    @Autowired
    private DeviceConfiguration deviceConfigurationHandler;

    @Autowired
    private DataTransmiterRepository dataTransmiterRepository;

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public static ResponseEntity handleTypeMismatch() {
        return new ResponseEntity(new ErrorDto("Invalid input"), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/client/send/parking-status", method = PUT)
    public ResponseEntity updateParkingStatusByDeviceId(@RequestParam(value = "deviceId") String deviceId, @RequestParam(value = "isParked") boolean isParked) {
        try {
            deviceConfigurationHandler.updateParkingStatus(deviceId, isParked);
        }
        catch(InvalidDeviceIdException exception) {
            return new ResponseEntity(new ErrorDto(exception), HttpStatus.BAD_REQUEST);
        }
        catch(InvalidInputException exception) {
            return new ResponseEntity(new ErrorDto(exception),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/client/send/timeout", method = PUT)
    public ResponseEntity updateTimeoutByDeviceId(@RequestParam(value = "deviceId") String deviceId, @RequestParam(value = "timeout", defaultValue = "0") long timeOut) {
        try {
            deviceConfigurationHandler.updateTimeOut(deviceId, timeOut);
        }
        catch(InvalidDeviceIdException exception) {
            return new ResponseEntity(new ErrorDto(exception), HttpStatus.BAD_REQUEST);
        }
        catch(InvalidInputException exception) {
            return new ResponseEntity(new ErrorDto(exception),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/client/send/parked-cordinates", method = PUT)
    public ResponseEntity updateTimeoutByDeviceId(@RequestParam(value = "deviceId") String deviceId,
                                                  @RequestParam(value = "x", defaultValue = "0") long x,
                                                  @RequestParam(value = "y", defaultValue = "0") long y) {
        try {
            deviceHandler.updateParkedCordinates(deviceId, x, y);
        }
        catch(InvalidDeviceIdException exception) {
            return new ResponseEntity(new ErrorDto(exception),HttpStatus.BAD_REQUEST);
        }
        catch(InvalidInputException exception) {
            return new ResponseEntity(new ErrorDto(exception),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/client/send/stolen-status", method = PUT)
    public ResponseEntity updateTimeoutByDeviceId(@RequestParam(value = "deviceId") String deviceId, @RequestParam(value = "isStolen") boolean isStolen) {
        try {
            deviceConfigurationHandler.updateStolenStatus(deviceId, isStolen);
        }
        catch(InvalidDeviceIdException exception) {
            return new ResponseEntity(new ErrorDto(exception), HttpStatus.BAD_REQUEST);
        }
        catch(InvalidInputException exception) {
            return new ResponseEntity(new ErrorDto(exception),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/client/send/create-new-user", method = POST)
    public ResponseEntity createNewUser(@RequestBody UserDto newUser) {
        try {
            userHandler.createNewUser(newUser);
        }
        catch(InvalidInputException exception) {
            return new ResponseEntity(new ErrorDto(exception), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/client/send/create-new-device", method = POST)
    public ResponseEntity createNewDevice(@RequestBody DeviceDto newDevice) {
        try {
            deviceHandler.createNewDevice(newDevice);
        }
        catch(InvalidInputException exception) {
            return new ResponseEntity(new ErrorDto(exception), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/client/{deviceId}/receive/gps-cordinates", method = GET)
    @ResponseBody
    public ResponseEntity<DataTransmiterInfo> getGpsCordinatesBydeviceId(@PathVariable(value = "deviceId") String deviceId) {
        try {
            DataTransmiterInfo dataTransmiterInfo = dataTransmiterHandler.getGPSCordinates(deviceId);
            return new ResponseEntity(dataTransmiterInfo,HttpStatus.OK);
        }
        catch(InvalidDeviceIdException exception) {
            return new ResponseEntity(new ErrorDto(exception), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/client/{deviceId}/receive/gps-cordinates-for-time", method = GET)
    @ResponseBody
    public ResponseEntity<DataTransmiterInfo> getGpsCordinatesForTimeStamp(@PathVariable(value = "deviceId") String deviceId, @RequestParam(value = "start") String start, @RequestParam(value = "end") String end) {
        try {
            DataTransmiterInfo dataTransmiterInfo = dataTransmiterHandler.getGPSCordinatesForDay(deviceId, start, end);
            return new ResponseEntity(dataTransmiterInfo,HttpStatus.OK);
        }
        catch(InvalidDeviceIdException exception) {
            return new ResponseEntity(new ErrorDto(exception), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/client/receive/user-account",method=GET)
    @ResponseBody
    public ResponseEntity getUserAccountByUsername(@RequestHeader("email") String email) {
        try {
            UserInfo userInfo = userHandler.getUser(email);
            return new ResponseEntity(userInfo,HttpStatus.OK);
        }
        catch(InvalidEmailException exception) {
            return new ResponseEntity(new ErrorDto(exception), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/client/{deviceId}/receive/device",method=GET)
    @ResponseBody
    public ResponseEntity geDeviceByDeviceID(@PathVariable(value = "deviceId") String deviceId) {
        try {
            DeviceInfo deviceInfo = deviceHandler.getDevice(deviceId);
            return new ResponseEntity(deviceInfo,HttpStatus.OK);
        }
        catch(InvalidDeviceIdException exception) {
            return new ResponseEntity(new ErrorDto(exception), HttpStatus.BAD_REQUEST);
        }
    }
}


