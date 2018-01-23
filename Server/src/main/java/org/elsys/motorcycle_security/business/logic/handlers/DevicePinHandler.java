package org.elsys.motorcycle_security.business.logic.handlers;

import org.elsys.motorcycle_security.business.logic.exceptions.InvalidDeviceIdException;
import org.elsys.motorcycle_security.dto.DevicePinInfo;
import org.elsys.motorcycle_security.models.DevicePin;
import org.elsys.motorcycle_security.repository.DevicePinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DevicePinHandler implements org.elsys.motorcycle_security.business.logic.DevicePin {
    @Autowired
    private DevicePinRepository devicePinRepository;

    @Override
    public DevicePinInfo getDevicePin(String deviceId) {
        DevicePin devicePin = devicePinRepository.getPinByDeviceId(deviceId);
        if(devicePin == null) throw new InvalidDeviceIdException("Invalid device id");
        return new DevicePinInfo(devicePin);
    }
}
