package org.elsys.motorcycle_security.repository;

import org.elsys.motorcycle_security.models.DeviceStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface DeviceStatusRepository extends CrudRepository<DeviceStatus,Long> {
    @Query("select isParked from DeviceStatus dev where dev.deviceid=:deviceId")
    boolean getParkingStatusByDeviceId(@Param("deviceId") long deviceId);
    @Query("select timeout from DeviceStatus dev where dev.deviceid=:deviceId")
    long getSettingsByDeviceId(@Param("deviceId") long deviceId);
    @Modifying
    @Transactional
    @Query("update DeviceStatus dev set dev.isParked=:IsParked where dev.deviceid=:deviceId")
    void updateParkingStatusByDeviceId(@Param("deviceId") long deviceId,@Param("IsParked") boolean IsParked);
}