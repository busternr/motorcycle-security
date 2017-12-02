package org.elsys.motorcycle_security.repository;

import org.elsys.motorcycle_security.models.Devices;
import org.elsys.motorcycle_security.models.Users;
import org.springframework.data.repository.CrudRepository;

public interface DevicesRepository extends CrudRepository<Devices,Long> {
}
