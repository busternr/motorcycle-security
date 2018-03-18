package org.elsys.motorcycle_security.info;

import org.elsys.motorcycle_security.models.DevicePin;

public class DevicePinInfo {
    private long id;
    private String pin;

    public DevicePinInfo(long id, String pin) {
        this.id = id;
        this.pin = pin;
    }

    public DevicePinInfo(DevicePin devicePin) {
        this(devicePin.getId(), devicePin.getPin());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}

