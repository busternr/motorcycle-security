package org.elsys.motorcycle_security.business.logic;

import org.elsys.motorcycle_security.SendGPSCords;
import org.elsys.motorcycle_security.models.DataTransmiter;
import org.elsys.motorcycle_security.repository.DataTransmiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;


@Component
public class DataTransmiterHandler {

    @Autowired
    private DataTransmiterRepository dataTransmiterRepository;

    public void UpdateGPSCords(Long deviceid,Long x,Long y){
        DataTransmiter d = new DataTransmiter();
        d.setDeviceid(deviceid);
        d.setX(x);
        d.setY(y);
        Long Date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String CurrDay = sdf.format(Date);
        d.setTime(CurrDay);
        dataTransmiterRepository.save(d);
    }
}
