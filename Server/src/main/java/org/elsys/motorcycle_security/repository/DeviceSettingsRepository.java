package org.elsys.motorcycle_security.repository;

import org.elsys.motorcycle_security.models.DeviceSettings;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface DeviceSettingsRepository extends CrudRepository<DeviceSettings,Long> {
    @Query("select isParked from DeviceSettings dev where dev.deviceId=:deviceId")
    boolean getParkingStatusByDeviceId(@Param("deviceId") long deviceId);
    @Query("select timeout from DeviceSettings dev where dev.deviceId=:deviceId")
    long getTimeoutByDeviceId(@Param("deviceId") long deviceId);
    @Modifying
    @Transactional
    @Query("update DeviceSettings dev set dev.isParked=:IsParked where dev.deviceId=:deviceId")
    void updateParkingStatusByDeviceId(@Param("deviceId") long deviceId,@Param("IsParked") boolean IsParked);
    @Modifying
    @Transactional
    @Query("update DeviceSettings dev set dev.timeout=:timeout where dev.deviceId=:deviceId")
    void updateTimeoutByDeviceId(@Param("deviceId") long deviceId,@Param("timeout") long timeout);
}