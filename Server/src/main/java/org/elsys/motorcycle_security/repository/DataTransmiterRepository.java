package org.elsys.motorcycle_security.repository;

import org.elsys.motorcycle_security.models.DataTransmiter;
import org.elsys.motorcycle_security.models.Devices;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataTransmiterRepository extends CrudRepository<DataTransmiter,Long> {
    @Query("select x,y from DataTransmiter dataTrans where dataTrans.device.id=:deviceId")
    List<DataTransmiter> getGpsCordinatesByDeviceId(@Param("deviceId") long deviceId);
}
