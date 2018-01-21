package org.elsys.motorcycle_security.dto;

import org.elsys.motorcycle_security.models.DataTransmiter;

import java.util.Date;

public class DataTransmiterInfo
{
    private double x;
    private double y;
    private Date date;

    public DataTransmiterInfo(double x, double y, Date date) {
        this.x = x;
        this.y = y;
        this.date = date;
    }

    public DataTransmiterInfo(DataTransmiter dataTransmiter) {
        this(dataTransmiter.getX(), dataTransmiter.getY(), dataTransmiter.getDate());
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
