package org.elsys.motorcycle_security.business.logic.handlers;

import org.elsys.motorcycle_security.business.logic.exceptions.InvalidDeviceIdException;
import org.elsys.motorcycle_security.business.logic.exceptions.UserDoesNotOwnDeviceException;
import org.elsys.motorcycle_security.dto.DataTransmitterDto;
import org.elsys.motorcycle_security.info.DataTransmitterInfo;
import org.elsys.motorcycle_security.models.DataTransmitter;
import org.elsys.motorcycle_security.models.Device;
import org.elsys.motorcycle_security.repository.DataTransmitterRepository;
import org.elsys.motorcycle_security.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DataTransmitterHandler extends AbstractHandler implements org.elsys.motorcycle_security.business.logic.DataTransmitter {
    @Autowired
    private DataTransmitterRepository dataTransmitterRepository;
    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public void updateGPSCoordinates(DataTransmitterDto dataTransmitterDto) {
        Device device = deviceRepository.getDeviceByDeviceId(dataTransmitterDto.getDeviceId());
        if(device == null) throw new InvalidDeviceIdException("Invalid device id");
        DataTransmitter dataTransmitter = new DataTransmitter();
        dataTransmitter.setDevice(device);
        dataTransmitter.setX(dataTransmitterDto.getX());
        dataTransmitter.setY(dataTransmitterDto.getY());
        dataTransmitter.setSpeed(dataTransmitterDto.getSpeed());
        Date date = new Date();
        dataTransmitter.setDate(date);
        dataTransmitterRepository.save(dataTransmitter);
        writeLog("Updated GPS coordinates for device:" + dataTransmitterDto.getDeviceId(), false);
    }

    @Override
    public DataTransmitterInfo getGPSCoordinates(String deviceId) {
        DataTransmitter dataTransmitter = dataTransmitterRepository.getGpsCoordinatesByDeviceId(deviceId);
        if(dataTransmitter == null) throw new InvalidDeviceIdException("Invalid device id");
        if(!checkUserOwnsDevice(new DataTransmitterDto(deviceId))) throw new UserDoesNotOwnDeviceException("This user doesn't own the specified device");
        writeLog("Received GPS coordinates for device:" + deviceId, true);
        return new DataTransmitterInfo(dataTransmitter);
    }

   @Override
    public List<DataTransmitterInfo> getGPSCoordinatesForDay(String deviceId, String day) {
       if(!checkUserOwnsDevice(new DataTransmitterDto(deviceId))) throw new UserDoesNotOwnDeviceException("This user doesn't own the specified device");
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String startFrom;
        String endFrom;
        List<DataTransmitterInfo> dataTransmitters = new ArrayList<>();
        for(int hour = 0; hour<24; hour++) {
            Date start_ = null;
            Date end_ = null;
            if(hour < 10) {
                startFrom = day + " 0" + hour + ":00:00";
                endFrom = day + " 0" + hour + ":59:59";
            }
            else {
                startFrom = day + " " + hour + ":00:00";
                endFrom = day + " " + hour + ":59:59";
            }
            try {
                start_ = formatter.parse(startFrom);
                end_ = formatter.parse(endFrom);
            } catch(java.text.ParseException exception) {}
            DataTransmitter dataTransmitter = dataTransmitterRepository.getGpsCoordinatesForDay(deviceId, start_, end_);
            if(dataTransmitter != null) {
                dataTransmitters.add(new DataTransmitterInfo(dataTransmitter));
            }
        }
        if(dataTransmitters.size() == 0) throw new InvalidDeviceIdException("Invalid device id");
        writeLog("Received GPS coordinates for date:" + day + " for device:" + deviceId, true);
        return dataTransmitters;
    }
}
