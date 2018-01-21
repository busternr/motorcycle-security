package org.elsys.motorcycle_security.business.logic;

import org.elsys.motorcycle_security.dto.DataTransmiterInfo;

import java.util.Date;

public interface DataTransmiter{

    void updateGPSCordinates(String deviceId, double x, double y);

    DataTransmiterInfo getGPSCordinates(String deviceId);

    DataTransmiterInfo getGPSCordinatesForDay(String deviceId, String start, String end);
}
