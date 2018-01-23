package org.elsys.motorcycle_security.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Device {

    @SerializedName("deviceId")
    @Expose
    private String deviceId;

    @SerializedName("userId")
    @Expose
    private long userId;

    @SerializedName("upTime")
    @Expose
    private long upTime;

    @SerializedName("parkedX")
    @Expose
    private double parkedX;

    @SerializedName("parkedY")
    @Expose
    private double parkedY;

    public Device() {}

    public Device(String deviceId, long userId) {
        this.deviceId = deviceId;
        this.userId = userId;
    }
    public Device(String deviceId, long upTime, double parkedX, double parkedY) {
        this.deviceId = deviceId;
        this.upTime = upTime;
        this.parkedX = parkedX;
        this.parkedY = parkedY;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getUpTime() {
        return upTime;
    }

    public void setUpTime(long upTime) {
        this.upTime = upTime;
    }

    public double getParkedX() {
        return parkedX;
    }

    public void setParkedX(double parkedX) {
        this.parkedX = parkedX;
    }

    public double getParkedY() {
        return parkedY;
    }

    public void setParkedY(double parkedY) {
        this.parkedY = parkedY;
    }
}