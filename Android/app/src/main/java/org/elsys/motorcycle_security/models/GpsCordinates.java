package org.elsys.motorcycle_security.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class GpsCordinates {

    @SerializedName("x")
    @Expose
    private double x;
    @SerializedName("y")
    @Expose
    private double y;
    @SerializedName("date")
    @Expose
    private long date;

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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}