package org.elsys.motorcycle_security.repository;

import org.elsys.motorcycle_security.models.DeviceConfiguration;
import org.springframework.data.repository.CrudRepository;

public interface DeviceConfigurationRepository extends CrudRepository<DeviceConfiguration,Long> {
//    @Query("select isParked from DevSet dev where dev.deviceId=:deviceId")
//    boolean getParkingStatusByDeviceId(@Param("deviceId") long deviceId);
//    @Query("select timeout from DevSet dev where dev.deviceId=:deviceId")
//    long getTimeoutByDeviceId(@Param("deviceId") long deviceId);
//    @Modifying
//    @Transactional
//    @Query("update DevSet dev set dev.isParked=:IsParked where dev.deviceId=:deviceId")
//    void updateParkingStatusByDeviceId(@Param("deviceId") long deviceId,@Param("IsParked") boolean IsParked);
//    @Modifying
//    @Transactional
//    @Query("update DevSet dev set dev.timeout=:timeout where dev.deviceId=:deviceId")
//    void updateTimeoutByDeviceId(@Param("deviceId") long deviceId,@Param("timeout") long timeout);
}