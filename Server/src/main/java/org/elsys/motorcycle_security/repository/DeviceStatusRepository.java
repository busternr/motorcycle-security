package org.elsys.motorcycle_security.repository;

import org.elsys.motorcycle_security.models.DataTransmiter;
import org.elsys.motorcycle_security.models.DeviceStatus;
import org.springframework.data.repository.CrudRepository;

public interface DeviceStatusRepository extends CrudRepository<DeviceStatus,Long> {
}
