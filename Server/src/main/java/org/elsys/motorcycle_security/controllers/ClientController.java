package org.elsys.motorcycle_security.controllers;

import org.elsys.motorcycle_security.business.logic.*;
import org.elsys.motorcycle_security.business.logic.exceptions.InvalidDeviceIdException;
import org.elsys.motorcycle_security.business.logic.exceptions.InvalidEmailException;
import org.elsys.motorcycle_security.business.logic.exceptions.InvalidInputException;
import org.elsys.motorcycle_security.business.logic.exceptions.InvalidUserIdException;
import org.elsys.motorcycle_security.dto.*;
import org.elsys.motorcycle_security.repository.DeviceRepository;
import org.elsys.motorcycle_security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
public class ClientController {
    @Autowired
    private User userHandler;
    @Autowired
    private Device deviceHandler;
    @Autowired
    private DataTransmiter dataTransmiterHandler;
    @Autowired
    private DeviceConfiguration deviceConfigurationHandler;
    @Autowired
    private DevicePin devicePinHandler;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private UserRepository userRepository;

    //private static final Logger log= Logger.getLogger(ClientController.class.getName());

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
                                                  @RequestParam(value = "x", defaultValue = "0") double x,
                                                  @RequestParam(value = "y", defaultValue = "0") double y) {
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

    @RequestMapping(value = "/client/send/change-password", method = PUT)
    public ResponseEntity updateTimeoutByDeviceId(@RequestParam(value = "userId") long userId, @RequestHeader(value = "oldPassword") String oldPassword, @RequestHeader(value = "newPassword") String newPassword) {
        try {
            userHandler.updatePassword(userId, oldPassword, newPassword);
        }
        catch(InvalidUserIdException exception) {
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

    @RequestMapping(value = "/client/{deviceId}/receive/gps-cordinates-for-day", method = GET)
    @ResponseBody
    public ResponseEntity<List<DataTransmiterInfo>> getGpsCordinatesForTimeStamp(@PathVariable(value = "deviceId") String deviceId, @RequestParam(value = "day") String day) {
        try {
            List<DataTransmiterInfo> dataTransmiterInfo = dataTransmiterHandler.getGPSCordinatesForDay(deviceId, day);
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

    @RequestMapping(value="/client/receive/user-account-only-email",method=GET)
    @ResponseBody
    public ResponseEntity getUserAccountByUsernameEmailOnly(@RequestHeader("email") String email) {
        try {
            UserInfo userInfo = new UserInfo();
            userInfo.setEmail(userRepository.getUserAccountByEmailOnlyEmail(email));
            if(userInfo.getEmail() == null) throw new InvalidEmailException("Invalid email");
            return new ResponseEntity(userInfo,HttpStatus.OK);
        }
        catch(InvalidEmailException exception) {
            return new ResponseEntity(new ErrorDto(exception), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/client/{deviceId}/receive/device",method=GET)
    @ResponseBody
    public ResponseEntity geDeviceByDeviceId(@PathVariable(value = "deviceId") String deviceId) {
        try {
            DeviceInfo deviceInfo = deviceHandler.getDevice(deviceId);
            return new ResponseEntity(deviceInfo,HttpStatus.OK);
        }
        catch(InvalidDeviceIdException exception) {
            return new ResponseEntity(new ErrorDto(exception), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/client/{deviceId}/receive/device-only-deviceid",method=GET)
    @ResponseBody
    public ResponseEntity getDeviceOnlyDeviceId(@PathVariable(value = "deviceId") String deviceId) {
        try {
            DeviceInfo deviceInfo = new DeviceInfo();
            deviceInfo.setDeviceId(deviceRepository.getDeviceOnlyDeviceId(deviceId));
            if(deviceInfo.getDeviceId() == null) throw new InvalidDeviceIdException("Invalid device id");
            return new ResponseEntity(deviceInfo,HttpStatus.OK);
        }
        catch(InvalidDeviceIdException exception) {
            return new ResponseEntity(new ErrorDto(exception), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/client/{deviceId}/receive/device-pin",method=GET)
    @ResponseBody
    public ResponseEntity geDevicePinByDeviceId(@PathVariable(value = "deviceId") String deviceId) {
        try {
            DevicePinInfo devicePinInfo = devicePinHandler.getDevicePin(deviceId);
            return new ResponseEntity(devicePinInfo,HttpStatus.OK);
        }
        catch(InvalidDeviceIdException exception) {
            return new ResponseEntity(new ErrorDto(exception), HttpStatus.BAD_REQUEST);
        }
    }
}


