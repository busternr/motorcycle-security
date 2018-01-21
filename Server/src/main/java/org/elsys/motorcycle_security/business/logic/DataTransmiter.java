package org.elsys.motorcycle_security.business.logic;

import org.elsys.motorcycle_security.dto.DataTransmiterInfo;

import java.util.List;

public interface DataTransmiter{

    void updateGPSCordinates(String deviceId, double x, double y);

    DataTransmiterInfo getGPSCordinates(String deviceId);

    List<DataTransmiterInfo> getGPSCordinatesForDay(String deviceId, String day);
}
