package org.elsys.motorcycle_security.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DevicePin {
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("pin")
    @Expose
    private String pin;

    public DevicePin(long id, String pin) {
        this.id = id;
        this.pin = pin;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}