package org.elsys.motorcycle_security.repository;

import org.elsys.motorcycle_security.models.DevicePin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DevicePinRepository extends CrudRepository<DevicePin, String> {
    @Query("select dev from DevicePin dev where dev.pin=:pin")
    DevicePin getPinByDeviceId(@Param("pin") String deviceId);
}
