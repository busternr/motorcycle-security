package org.elsys.motorcycle_security.repository;

import org.elsys.motorcycle_security.models.DataTransmiter;
import org.elsys.motorcycle_security.models.Devices;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DevicesRepository extends CrudRepository<Devices,Long> {
    @Query("select dev from Devices dev where dev.deviceId=:deviceId")
    Devices getDeviceByDeviceId(@Param("deviceId") String deviceId);
    @Query("select dev from Devices dev where dev.id=:Id")
    Devices getDeviceById(@Param("Id") long Id);
}
