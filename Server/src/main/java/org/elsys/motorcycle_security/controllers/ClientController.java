package org.elsys.motorcycle_security.controllers;

import org.elsys.motorcycle_security.business.logic.*;
import org.elsys.motorcycle_security.business.logic.exceptions.InvalidDeviceIdException;
import org.elsys.motorcycle_security.business.logic.exceptions.InvalidEmailException;
import org.elsys.motorcycle_security.business.logic.exceptions.InvalidInputException;
import org.elsys.motorcycle_security.business.logic.exceptions.UserDoesNotOwnDeviceException;
import org.elsys.motorcycle_security.dto.*;
import org.elsys.motorcycle_security.info.*;
import org.elsys.motorcycle_security.repository.DeviceRepository;
import org.elsys.motorcycle_security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

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
    private DataTransmitter dataTransmitterHandler;
    @Autowired
    private DeviceConfiguration deviceConfigurationHandler;
    @Autowired
    private DevicePin devicePinHandler;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private UserRepository userRepository;


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public static ResponseEntity handleTypeMismatchException() {
        return new ResponseEntity(new ErrorDto("Invalid input"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public static ResponseEntity handleNotReadableException() {
        return new ResponseEntity(new ErrorDto("Invalid input"), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/client/send/parking-status", method = PUT)
    public ResponseEntity updateParkingStatusByDeviceId(@RequestBody DeviceConfigurationDto deviceConfigurationDto) throws Exception {
        try {

            deviceConfigurationHandler.updateParkingStatus(deviceConfigurationDto);
        }
        catch(InvalidDeviceIdException exception) {
            return new ResponseEntity(new ErrorDto(exception), HttpStatus.BAD_REQUEST);
        }
        catch(InvalidInputException exception) {
            return new ResponseEntity(new ErrorDto(exception),HttpStatus.BAD_REQUEST);
        }
        catch (UserDoesNotOwnDeviceException exception) {
            return new ResponseEntity(new ErrorDto(exception),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/client/send/timeout", method = PUT)
    public ResponseEntity updateTimeoutByDeviceId(@RequestBody DeviceConfigurationDto deviceConfigurationDto) throws Exception {
        try {
            deviceConfigurationHandler.updateTimeOut(deviceConfigurationDto);
        }
        catch(InvalidDeviceIdException exception) {
            return new ResponseEntity(new ErrorDto(exception), HttpStatus.BAD_REQUEST);
        }
        catch(InvalidInputException exception) {
            return new ResponseEntity(new ErrorDto(exception),HttpStatus.BAD_REQUEST);
        }
        catch (UserDoesNotOwnDeviceException exception) {
            return new ResponseEntity(new ErrorDto(exception),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/client/send/parked-coordinates", method = PUT)
    public ResponseEntity updateParkedCoordinates(@RequestBody DeviceDto deviceDto) {
        try {
            deviceHandler.updateParkedCoordinates(deviceDto);
        }
        catch(InvalidDeviceIdException exception) {
            return new ResponseEntity(new ErrorDto(exception),HttpStatus.BAD_REQUEST);
        }
        catch(InvalidInputException exception) {
            return new ResponseEntity(new ErrorDto(exception),HttpStatus.BAD_REQUEST);
        }
        catch (UserDoesNotOwnDeviceException exception) {
            return new ResponseEntity(new ErrorDto(exception),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/client/send/stolen-status", method = PUT)
    public ResponseEntity updateStolenStatusByDeviceId(@RequestBody DeviceConfigurationDto deviceConfigurationDto) {
        try {
            deviceConfigurationHandler.updateStolenStatus(deviceConfigurationDto);
        }
        catch(InvalidDeviceIdException exception) {
            return new ResponseEntity(new ErrorDto(exception), HttpStatus.BAD_REQUEST);
        }
        catch(InvalidInputException exception) {
            return new ResponseEntity(new ErrorDto(exception),HttpStatus.BAD_REQUEST);
        }
        catch (UserDoesNotOwnDeviceException exception) {
            return new ResponseEntity(new ErrorDto(exception),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/client/send/change-password", method = PUT)
    public ResponseEntity updateTimeoutByDeviceId(@RequestHeader(value = "email") String email, @RequestHeader(value = "newPassword") String newPassword) {
        try {
            userHandler.updatePassword(email, newPassword);
        }
        catch(InvalidEmailException exception) {
            return new ResponseEntity(new ErrorDto(exception), HttpStatus.BAD_REQUEST);
        }
        catch(InvalidInputException exception) {
            return new ResponseEntity(new ErrorDto(exception),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/client/send/create-new-user", method = POST)
    public ResponseEntity createNewUser(@RequestBody UserDto userDto) {
        try {
            userHandler.createNewUser(userDto);
        }
        catch(InvalidInputException exception) {
            return new ResponseEntity(new ErrorDto(exception), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/client/send/create-new-device", method = POST)
    public ResponseEntity createNewDevice(@RequestBody DeviceDto deviceDto) {
        try {
            deviceHandler.createNewDevice(deviceDto);
        }
        catch(InvalidInputException exception) {
            return new ResponseEntity(new ErrorDto(exception), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/client/{deviceId}/receive/gps-coordinates", method = GET)
    @ResponseBody
    public ResponseEntity<DataTransmitterInfo> getGpsCoordinatesByDeviceId(@PathVariable(value = "deviceId") String deviceId) {
        try {
            DataTransmitterInfo dataTransmitterInfo = dataTransmitterHandler.getGPSCoordinates(deviceId);
            return new ResponseEntity(dataTransmitterInfo,HttpStatus.OK);
        }
        catch(InvalidDeviceIdException exception) {
            return new ResponseEntity(new ErrorDto(exception), HttpStatus.BAD_REQUEST);
        }
        catch (UserDoesNotOwnDeviceException exception) {
            return new ResponseEntity(new ErrorDto(exception),HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/client/{deviceId}/receive/gps-coordinates-for-day", method = GET)
    @ResponseBody
    public ResponseEntity<List<DataTransmitterInfo>> getGpsCoordinatesForTimeStamp(@PathVariable(value = "deviceId") String deviceId, @RequestParam(value = "day") String day) {
        try {
            List<DataTransmitterInfo> dataTransmitterInfo = dataTransmitterHandler.getGPSCoordinatesForDay(deviceId, day);
            return new ResponseEntity(dataTransmitterInfo,HttpStatus.OK);
        }
        catch(InvalidDeviceIdException exception) {
            return new ResponseEntity(new ErrorDto(exception), HttpStatus.BAD_REQUEST);
        }
        catch (UserDoesNotOwnDeviceException exception) {
            return new ResponseEntity(new ErrorDto(exception),HttpStatus.BAD_REQUEST);
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
        catch (UserDoesNotOwnDeviceException exception) {
            return new ResponseEntity(new ErrorDto(exception),HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/client/{deviceId}/receive/device-only-deviceId",method=GET)
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

    @RequestMapping(value="/client/{deviceId}/receive/device-configuration", method=GET)
    @ResponseBody
    public ResponseEntity getDeviceConfigurationByDeviceId(@PathVariable (value="deviceId") String deviceId) {
        DeviceConfigurationInfo deviceConfigurationInfo;
        try {
            deviceConfigurationInfo = deviceConfigurationHandler.getDeviceConfiguration(deviceId);
        }
        catch(InvalidDeviceIdException exception) {
            return new ResponseEntity(new ErrorDto(exception),HttpStatus.BAD_REQUEST);
        }
        catch (UserDoesNotOwnDeviceException exception) {
            return new ResponseEntity(new ErrorDto(exception),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(deviceConfigurationInfo,HttpStatus.OK);
    }
}


