package org.elsys.motorcycle_security.business.logic;

import org.elsys.motorcycle_security.dto.DataTransmiterInfo;

public interface DataTransmiter{

    void updateGPSCordinates(String deviceId, Long x, Long y);

    DataTransmiterInfo getGPSCordinates(String deviceId);
}
