package org.elsys.motorcycle_security.business.logic;

import org.elsys.motorcycle_security.info.DevicePinInfo;

public interface DevicePin {
    DevicePinInfo getDevicePin(String deviceId);
}
