package org.elsys.motorcycle_security.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeviceConfiguration {

    @SerializedName("deviceId")
    @Expose
    private String deviceId;
    @SerializedName("timeOut")
    @Expose
    private long timeOut;

    @SerializedName("radius")
    @Expose
    private long radius;
    @SerializedName("parked")
    @Expose
    private boolean parked;
    @SerializedName("stolen")
    @Expose
    private boolean stolen;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }

    public long getRadius() {
        return radius;
    }

    public void setRadius(long radius) {
        this.radius = radius;
    }

    public boolean isParked() {
        return parked;
    }

    public void setParked(boolean parked) {
        this.parked = parked;
    }

    public boolean isStolen() {
        return stolen;
    }

    public void setStolen(boolean stolen) {
        this.stolen = stolen;
    }
}