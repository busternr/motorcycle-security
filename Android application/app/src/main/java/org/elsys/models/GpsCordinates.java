package org.elsys.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GpsCordinates {

    @SerializedName("x")
    @Expose
    private long x;
    @SerializedName("y")
    @Expose
    private long y;

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