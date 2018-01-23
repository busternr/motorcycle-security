package org.elsys.motorcycle_security.models;

import javax.persistence.*;

@Entity
@Table(name = "DevicePins", uniqueConstraints={@UniqueConstraint(columnNames={"pin"})})
public class DevicePin {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name="Pin",unique=true)
    private String pin;

    public String getPin() {
        return pin;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}