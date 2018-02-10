package org.elsys.motorcycle_security.models;

import javax.persistence.*;

@Entity
@Table(name = "DevicePins", uniqueConstraints={@UniqueConstraint(columnNames={"pin"}), @UniqueConstraint(columnNames={"token"})})
public class DevicePin {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name="Pin",unique=true)
    private String pin;

    @Column(name="token",unique=true)
    private String token;

    @Column(name="tokenUsed")
    private boolean tokenUsed;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isTokenUsed() {
        return tokenUsed;
    }

    public void setTokenUsed(boolean tokenUsed) {
        this.tokenUsed = tokenUsed;
    }
}