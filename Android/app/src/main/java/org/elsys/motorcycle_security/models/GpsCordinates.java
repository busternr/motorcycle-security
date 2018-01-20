package org.elsys.motorcycle_security.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GpsCordinates {

    @SerializedName("x")
    @Expose
    private double x;
    @SerializedName("y")
    @Expose
    private double y;
    @SerializedName("time")
    @Expose
    private long time;

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