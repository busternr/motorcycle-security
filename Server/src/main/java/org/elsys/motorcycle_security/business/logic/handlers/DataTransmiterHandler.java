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
import java.util.Date;

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
        Long time = utc.toInstant().toEpochMilli()/1000;
        java.sql.Date date_ = new java.sql.Date(date.getTime());
        d.setTime(date_);
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
    public DataTransmiterInfo getGPSCordinatesForDay(String deviceId, String start, String end) {
        DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        Date start_ = null;
        Date end_ = null;
        try {
            start_ = formatter.parse(start);
            end_ = formatter.parse(end);
        }  catch(java.text.ParseException exception) {}

        DataTransmiter dataTransmiter = dataTransmiterRepository.getGpsCordinatesForDay(deviceId, start_, end_);
        if(dataTransmiter == null) throw new InvalidDeviceIdException("Invalid device id");
        return new DataTransmiterInfo(dataTransmiter);
    }
}
