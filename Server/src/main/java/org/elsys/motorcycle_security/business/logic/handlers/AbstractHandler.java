package org.elsys.motorcycle_security.business.logic.handlers;

import org.elsys.motorcycle_security.dto.AbstractDto;
import org.elsys.motorcycle_security.models.Device;
import org.elsys.motorcycle_security.models.User;
import org.elsys.motorcycle_security.repository.DeviceRepository;
import org.elsys.motorcycle_security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class AbstractHandler {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DeviceRepository deviceRepository;

    public boolean checkUserOwnsDevice(AbstractDto abstractDto) {
        boolean found = false;
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        String deviceId = abstractDto.getDeviceId();
        User user = userRepository.getUserAccountByEmail(email);
        Device device = deviceRepository.getDeviceByDeviceId(deviceId);
        for(Device checkDevice : user.getUserDevices()) {
            if(device.getDeviceId().equals(checkDevice.getDeviceId())) found = true;
        }
        return found;
    }

    public void writeLog(String message, boolean type) {
        try {
            String filename;
            if(type) filename = "log/client.log";
            else filename = "log/device.log";
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename, true));
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date = new Date();
            bufferedWriter.write(formatter.format(date) + " || " + message);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (java.io.IOException ex) {}
    }
}
