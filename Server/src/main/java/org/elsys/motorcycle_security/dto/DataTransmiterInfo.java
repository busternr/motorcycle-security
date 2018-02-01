package org.elsys.motorcycle_security.dto;

import org.elsys.motorcycle_security.models.DataTransmiter;

import java.util.Date;

public class DataTransmiterInfo
{
    private double x;
    private double y;
    private double speed;
    private Date date;

    public DataTransmiterInfo(double x, double y, double speed, Date date) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.date = date;
    }

    public DataTransmiterInfo(DataTransmiter dataTransmiter) {
        this(dataTransmiter.getX(), dataTransmiter.getY(), dataTransmiter.getSpeed(), dataTransmiter.getDate());
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

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
