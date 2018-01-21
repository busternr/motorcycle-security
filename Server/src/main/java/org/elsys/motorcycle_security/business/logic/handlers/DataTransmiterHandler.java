package org.elsys.motorcycle_security.business.logic.handlers;

import org.elsys.motorcycle_security.business.logic.exceptions.InvalidDeviceIdException;
import org.elsys.motorcycle_security.business.logic.exceptions.InvalidInputException;
import org.elsys.motorcycle_security.dto.DataTransmiterInfo;
import org.elsys.motorcycle_security.models.DataTransmiter;
import org.elsys.motorcycle_security.models.Device;
import org.elsys.motorcycle_security.repository.DataTransmiterRepository;
import org.elsys.motorcycle_security.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DataTransmiterHandler implements org.elsys.motorcycle_security.business.logic.DataTransmiter {
    @Autowired
    private DataTransmiterRepository dataTransmiterRepository;
    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public void updateGPSCordinates(String deviceId, double x, double y) {
        Device device = deviceRepository.getDeviceByDeviceId(deviceId);
        if(device == null) throw new InvalidDeviceIdException("Invalid device id");
        if(x == 0 || y == 0) throw new InvalidInputException("Invalid input");
        DataTransmiter d = new DataTransmiter();
        d.setX(x);
        d.setY(y);
        Date date = new Date();
        ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
        d.setDate(date);
        d.setDevice(device);
        dataTransmiterRepository.save(d);
    }

    @Override
    public DataTransmiterInfo getGPSCordinates(String deviceId) {
        DataTransmiter dataTransmiter = dataTransmiterRepository.getGpsCordinatesByDeviceId(deviceId);
        if(dataTransmiter == null) throw new InvalidDeviceIdException("Invalid device id");
        return new DataTransmiterInfo(dataTransmiter);
    }

    @Override
    public List<DataTransmiterInfo> getGPSCordinatesForDay(String deviceId, String day) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String startFrom;
        String endFrom;
        List<DataTransmiterInfo> dataTransmiters = new ArrayList<>();
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
            System.out.println("start=" + startFrom + " end=" + endFrom);
            DataTransmiter dataTransmiter = dataTransmiterRepository.getGpsCordinatesForDay(deviceId, start_, end_);
            if(dataTransmiter != null) {
                dataTransmiters.add(new DataTransmiterInfo(dataTransmiter));
                System.out.println("Found");
            }
        }
        if(dataTransmiters.size() == 0) throw new InvalidDeviceIdException("Invalid device id");
        return dataTransmiters;
    }
}
