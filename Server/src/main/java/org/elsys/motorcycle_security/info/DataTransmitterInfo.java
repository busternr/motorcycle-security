package org.elsys.motorcycle_security.info;

import org.elsys.motorcycle_security.models.DataTransmitter;

import java.util.Date;

public class DataTransmitterInfo
{
    private double x;
    private double y;
    private double speed;
    private Date date;

    public DataTransmitterInfo(double x, double y, double speed, Date date) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.date = date;
    }

    public DataTransmitterInfo(DataTransmitter dataTransmitter) {
        this(dataTransmitter.getX(), dataTransmitter.getY(), dataTransmitter.getSpeed(), dataTransmitter.getDate());
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
