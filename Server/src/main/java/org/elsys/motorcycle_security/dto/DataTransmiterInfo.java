package org.elsys.motorcycle_security.dto;

import org.elsys.motorcycle_security.models.DataTransmiter;
import org.elsys.motorcycle_security.models.Device;

import java.util.ArrayList;
import java.util.List;

public class DataTransmiterInfo
{
    private double x;
    private double y;
    private long time;

    public DataTransmiterInfo(double x, double y, long time) {
        this.x = x;
        this.y = y;
        this.time = time;
    }

    public DataTransmiterInfo(DataTransmiter dataTransmiter) {
        this(dataTransmiter.getX(), dataTransmiter.getY(), dataTransmiter.getTime());
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
