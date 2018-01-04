package org.elsys.motorcycle_security.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeviceConfiguration {

    @SerializedName("timeOut")
    @Expose
    private long timeOut;
    @SerializedName("parked")
    @Expose
    private boolean parked;

    public long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }

    public boolean isParked() {
        return parked;
    }

    public void setParked(boolean parked) {
        this.parked = parked;
    }
}