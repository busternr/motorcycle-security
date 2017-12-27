package org.elsys.motorcycle_security.repository;

import org.elsys.motorcycle_security.models.DeviceConfiguration;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface DeviceConfigurationRepository extends CrudRepository<DeviceConfiguration,Long> {
    @Query("select dev from DeviceConfiguration dev where dev.device.deviceId=:deviceId")
    DeviceConfiguration getDeviceConfigurationDeviceId(@Param("deviceId") String deviceId);
    @Modifying
    @Transactional
    @Query("update DeviceConfiguration dev set dev.isParked=:IsParked where dev.device.id=:deviceId")
    void updateParkingStatusByDeviceId(@Param("deviceId") long deviceId,@Param("IsParked") boolean IsParked);
    @Modifying
    @Transactional
    @Query("update DeviceConfiguration dev set dev.timeOut=:timeOut where dev.device.id=:deviceId")
    void updateTimeoutByDeviceId(@Param("deviceId") long deviceId,@Param("timeOut") long timeOut);
}