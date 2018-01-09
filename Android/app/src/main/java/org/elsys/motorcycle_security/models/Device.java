package org.elsys.motorcycle_security.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Device {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("deviceId")
    @Expose
    private String deviceId;

    @SerializedName("upTime")
    @Expose
    private long upTime;

    @SerializedName("parkedX")
    @Expose
    private long parkedX;

    @SerializedName("parkedY")
    @Expose
    private long parkedY;

    public Device() {}
    public Device(String deviceId, long upTime, long parkedX, long parkedY) {
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

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUpTime() {
        return upTime;
    }

    public void setUpTime(long upTime) {
        this.upTime = upTime;
    }

    public long getParkedX() {
        return parkedX;
    }

    public void setParkedX(long parkedX) {
        this.parkedX = parkedX;
    }

    public long getParkedY() {
        return parkedY;
    }

    public void setParkedY(long parkedY) {
        this.parkedY = parkedY;
    }
}