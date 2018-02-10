package org.elsys.motorcycle_security.repository;

import org.elsys.motorcycle_security.models.DataTransmitter;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;


@Repository
public interface DataTransmitterRepository extends CrudRepository<DataTransmitter,Long> {
    @Query("select MAX(dataTrans) from DataTransmitter dataTrans where dataTrans.device.deviceId=:deviceId")
    DataTransmitter getGpsCoordinatesByDeviceId(@Param("deviceId") String deviceId);

    @Query("SELECT MIN(dataTrans) FROM DataTransmitter dataTrans WHERE dataTrans.device.deviceId=:deviceId and dataTrans.date BETWEEN :start AND :endd")
    DataTransmitter getGpsCoordinatesForDay(@Param("deviceId") String deviceId, @Param("start") Date start, @Param("endd") Date endd);
}
