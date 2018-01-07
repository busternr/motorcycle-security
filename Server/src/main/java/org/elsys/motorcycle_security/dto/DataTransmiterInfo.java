package org.elsys.motorcycle_security.dto;

import org.elsys.motorcycle_security.models.DataTransmiter;
import org.elsys.motorcycle_security.models.Device;

import java.util.ArrayList;
import java.util.List;

public class DataTransmiterInfo
{
    private long x;
    private long y;
    private long time;

    public DataTransmiterInfo(long x, long y, long time) {
        this.x = x;
        this.y = y;
        this.time = time;
    }

    public DataTransmiterInfo(DataTransmiter dataTransmiter) {
        this(dataTransmiter.getX(), dataTransmiter.getY(), dataTransmiter.getTime());
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
