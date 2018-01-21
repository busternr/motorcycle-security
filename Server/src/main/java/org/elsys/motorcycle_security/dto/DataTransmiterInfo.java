package org.elsys.motorcycle_security.dto;

import org.elsys.motorcycle_security.models.DataTransmiter;

import java.util.Date;

public class DataTransmiterInfo
{
    private double x;
    private double y;
    private Date time;

    public DataTransmiterInfo(double x, double y, Date time) {
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
