package org.elsys.motorcycle_security.dto;

import org.elsys.motorcycle_security.models.DataTransmiter;
import org.elsys.motorcycle_security.models.Device;

import java.util.ArrayList;
import java.util.List;

public class DataTransmiterInfo
{
    private long x;
    private long y;
    List<Device> devices = new ArrayList<Device>();

    public DataTransmiterInfo(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public DataTransmiterInfo(DataTransmiter dataTransmiter) {
        this(dataTransmiter.getX(), dataTransmiter.getY());
    }

    public long getX() {
        return x;
    }

    public void setX(long x) {
        this.x = x;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }
}
