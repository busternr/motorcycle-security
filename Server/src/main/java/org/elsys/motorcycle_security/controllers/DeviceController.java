package org.elsys.motorcycle_security.controllers;

import org.elsys.motorcycle_security.business.logic.DataTransmiter;
import org.elsys.motorcycle_security.business.logic.DeviceConfiguration;
import org.elsys.motorcycle_security.business.logic.exceptions.AlreadyUsedTokenException;
import org.elsys.motorcycle_security.business.logic.exceptions.InvalidDeviceIdException;
import org.elsys.motorcycle_security.business.logic.exceptions.InvalidDeviceTokenException;
import org.elsys.motorcycle_security.business.logic.exceptions.InvalidInputException;
import org.elsys.motorcycle_security.dto.DeviceConfigurationInfo;
import org.elsys.motorcycle_security.dto.ErrorDto;
import org.elsys.motorcycle_security.models.DevicePin;
import org.elsys.motorcycle_security.repository.DevicePinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class DeviceController {
    @Autowired
    private DataTransmiter dataTransmiterHandler;
    @Autowired
    private DeviceConfiguration deviceConfigurationHandler;
    @Autowired
    private DevicePinRepository devicePinRepository;

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public static ResponseEntity handleTypeMismatch() {
        return new ResponseEntity(new ErrorDto("Invalid input"), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/device/send/gps-cordinates",method=POST)
    public ResponseEntity sendGpsCordinates(@RequestHeader(value = "device-token") String token,
                                            @RequestParam(value="deviceId") String deviceId,
                                            @RequestParam(value="x", defaultValue="0") double x,
                                            @RequestParam(value="y", defaultValue="0") double y,
                                            @RequestParam(value="speed", defaultValue="0") double speed) {
        try {
            dataTransmiterHandler.updateGPSCordinates(deviceId, x, y, speed);
            DevicePin devicePin = devicePinRepository.getPinByDeviceId(deviceId);
            if(!token.matches(devicePin.getToken())) throw new InvalidDeviceTokenException("Invalid device token");
        }
        catch(InvalidDeviceTokenException exception) {
            return new ResponseEntity(new ErrorDto(exception),HttpStatus.BAD_REQUEST);
        }
        catch(InvalidDeviceIdException exception) {
            return new ResponseEntity(new ErrorDto(exception),HttpStatus.BAD_REQUEST);
        }
        catch(InvalidInputException exception) {
            return new ResponseEntity(new ErrorDto(exception),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value="/device/{deviceId}/receive/device-configuration", method=GET)
    @ResponseBody
    public ResponseEntity getDeviceConfigurationByDeviceId(@RequestHeader(value = "device-token") String token, @PathVariable (value="deviceId") String deviceId) {
        DeviceConfigurationInfo deviceConfigurationInfo;
        try {
            deviceConfigurationInfo = deviceConfigurationHandler.getDeviceConfiguration(deviceId);
            DevicePin devicePin = devicePinRepository.getPinByDeviceId(deviceId);
            if(!token.matches(devicePin.getToken())) throw new InvalidDeviceTokenException("Invalid device token");
        }
        catch(InvalidDeviceTokenException exception) {
            return new ResponseEntity(new ErrorDto(exception),HttpStatus.BAD_REQUEST);
        }
        catch(InvalidDeviceIdException exception) {
            return new ResponseEntity(new ErrorDto(exception),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(deviceConfigurationInfo,HttpStatus.OK);
    }

    @RequestMapping(value="/device/{deviceId}/receive/device-token", method=GET)
    public ResponseEntity getDeviceTokenByDeviceId(@PathVariable (value="deviceId") String deviceId, HttpServletResponse response) {
        try {
            DevicePin devicePin = devicePinRepository.getPinByDeviceId(deviceId);
            if(devicePin == null) throw new InvalidDeviceIdException("Invalid device id");
            if(devicePin.isTokenUsed()) throw new AlreadyUsedTokenException("Token is already used");
            //devicePin.setTokenUsed(true);
            //devicePinRepository.save(devicePin);
            response.addHeader("device-token", devicePin.getToken());

        }
        catch(InvalidDeviceIdException exception) {
            return new ResponseEntity(new ErrorDto(exception),HttpStatus.BAD_REQUEST);
        }
        catch(AlreadyUsedTokenException exception) {
            return new ResponseEntity(new ErrorDto(exception),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}