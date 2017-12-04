package org.elsys.motorcycle_security.repository;

import org.elsys.motorcycle_security.models.DataTransmiter;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataTransmiterRepository extends CrudRepository<DataTransmiter,Long> {
//dataTrans.deviceid e poleto ot bazata/entity
    //:deviceId e named parameter i se vzima ot methoda
    @Query("select x,y from DataTransmiter dataTrans where dataTrans.deviceid=:deviceId")
    List<DataTransmiter> getGpsCordinatesByDeviceId(@Param("deviceId") long deviceId);
}
