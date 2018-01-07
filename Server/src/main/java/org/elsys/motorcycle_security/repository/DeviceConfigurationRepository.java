package org.elsys.motorcycle_security.repository;

import org.elsys.motorcycle_security.models.DeviceConfiguration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DeviceConfigurationRepository extends CrudRepository<DeviceConfiguration,Long> {
    @Query("select dev from DeviceConfiguration dev where dev.device.deviceId=:deviceId")
    DeviceConfiguration getDeviceConfigurationByDeviceId(@Param("deviceId") String deviceId);
}