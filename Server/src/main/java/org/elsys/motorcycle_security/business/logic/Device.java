package org.elsys.motorcycle_security.business.logic;

import org.elsys.motorcycle_security.dto.DeviceDto;
import org.elsys.motorcycle_security.dto.DeviceInfo;

public interface Device {
    void createNewDevice(DeviceDto deviceDto);

    DeviceInfo getDevice(String deviceId);
}
