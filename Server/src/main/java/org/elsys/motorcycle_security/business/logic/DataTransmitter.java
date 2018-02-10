package org.elsys.motorcycle_security.business.logic;

import org.elsys.motorcycle_security.dto.DataTransmitterDto;
import org.elsys.motorcycle_security.dto.DataTransmitterInfo;

import java.util.List;

public interface DataTransmitter {
    void updateGPSCoordinates(DataTransmitterDto dataTransmitterDto);

    DataTransmitterInfo getGPSCoordinates(String deviceId);

    List<DataTransmitterInfo> getGPSCoordinatesForDay(String deviceId, String day);
}
