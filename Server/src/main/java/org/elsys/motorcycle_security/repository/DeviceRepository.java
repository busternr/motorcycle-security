package org.elsys.motorcycle_security.repository;

import org.elsys.motorcycle_security.models.Device;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DeviceRepository extends CrudRepository<Device,Long> {
    @Query("select dev from Device dev where dev.deviceId=:deviceId")
    Device getDeviceByDeviceId(@Param("deviceId") String deviceId);
    @Query("select dev from Device dev where dev.id=:Id")
    Device getDeviceById(@Param("Id") long Id);
}
