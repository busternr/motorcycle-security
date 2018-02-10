package org.elsys.motorcycle_security.business.logic;

import org.elsys.motorcycle_security.dto.DataTransmiterInfo;

import java.util.List;

public interface DataTransmiter{

    void updateGPSCoordinates(String deviceId, double x, double y, double speed);

    DataTransmiterInfo getGPSCoordinates(String deviceId);

    List<DataTransmiterInfo> getGPSCoordinatesForDay(String deviceId, String day);
}
