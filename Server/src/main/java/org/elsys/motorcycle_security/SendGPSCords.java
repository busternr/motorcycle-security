package org.elsys.motorcycle_security;

public class SendGPSCords {
    private final double x;
    private final double y;

    public SendGPSCords(double x_, double y_) {
        this.x = x_;
        this.y = y_;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
