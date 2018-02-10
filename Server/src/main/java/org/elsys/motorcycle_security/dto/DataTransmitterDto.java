package org.elsys.motorcycle_security.dto;

import javax.validation.constraints.NotNull;

public class DataTransmitterDto {
    @NotNull
    private String deviceId;

    @NotNull
    private double x;

    @NotNull
    private double y;

    @NotNull
    private double speed;

    public DataTransmitterDto() {
    }

    public DataTransmitterDto(String deviceId, double x, double y, double speed) {
        this.deviceId = deviceId;
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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
}