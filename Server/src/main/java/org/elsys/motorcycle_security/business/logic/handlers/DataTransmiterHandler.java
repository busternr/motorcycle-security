package org.elsys.motorcycle_security.business.logic.handlers;

import org.elsys.motorcycle_security.business.logic.exceptions.InvalidDeviceIdException;
import org.elsys.motorcycle_security.business.logic.exceptions.InvalidInputException;
import org.elsys.motorcycle_security.dto.DataTransmiterDto;
import org.elsys.motorcycle_security.dto.DataTransmiterInfo;
import org.elsys.motorcycle_security.models.DataTransmiter;
import org.elsys.motorcycle_security.models.Device;
import org.elsys.motorcycle_security.repository.DataTransmiterRepository;
import org.elsys.motorcycle_security.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.xml.crypto.Data;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    public void updateGPSCoordinates(DataTransmiterDto dataTransmiterDto) {
        Device device = deviceRepository.getDeviceByDeviceId(dataTransmiterDto.getDeviceId());
        if(device == null) throw new InvalidDeviceIdException("Invalid device id");
        DataTransmiter dataTransmiter = new DataTransmiter();
        dataTransmiter.setDevice(device);
        dataTransmiter.setX(dataTransmiterDto.getX());
        dataTransmiter.setY(dataTransmiterDto.getY());
        dataTransmiter.setSpeed(dataTransmiterDto.getSpeed());
        Date date = new Date();
        dataTransmiter.setDate(date);
        dataTransmiterRepository.save(dataTransmiter);
    }

    @Override
    public DataTransmiterInfo getGPSCoordinates(String deviceId) {
        DataTransmiter dataTransmiter = dataTransmiterRepository.getGpsCoordinatesByDeviceId(deviceId);
        if(dataTransmiter == null) throw new InvalidDeviceIdException("Invalid device id");
        return new DataTransmiterInfo(dataTransmiter);
    }

   @Override
    public List<DataTransmiterInfo> getGPSCoordinatesForDay(String deviceId, String day) {
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
            DataTransmiter dataTransmiter = dataTransmiterRepository.getGpsCoordinatesForDay(deviceId, start_, end_);
            if(dataTransmiter != null) {
                dataTransmiters.add(new DataTransmiterInfo(dataTransmiter));
            }
        }
        if(dataTransmiters.size() == 0) throw new InvalidDeviceIdException("Invalid device id");
        return dataTransmiters;
    }
}
