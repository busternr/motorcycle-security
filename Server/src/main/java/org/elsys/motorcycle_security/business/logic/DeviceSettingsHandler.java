package org.elsys.motorcycle_security.business.logic;

import org.elsys.motorcycle_security.models.DeviceSettings;
import org.elsys.motorcycle_security.repository.DeviceSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeviceSettingsHandler {
    @Autowired
    private DeviceSettingsRepository DeviceSettingsRepository;

    public void createDeviceSettings(long deviceid){
        DeviceSettings d = new DeviceSettings();
        d.setDeviceid(deviceid);
        d.setParked(false);
        d.setTimeout(300000);
        DeviceSettingsRepository.save(d);
    }
}
