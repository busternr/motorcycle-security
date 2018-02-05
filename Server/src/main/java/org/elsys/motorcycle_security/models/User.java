package org.elsys.motorcycle_security.models;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "Users",uniqueConstraints={@UniqueConstraint(columnNames={"email"})})
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name="Email",unique=true)
    private String email;

    @Column(name="Password")
    private String password;

    @OneToMany(mappedBy="user")
    private List<Device> userDevices=new ArrayList<>();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email_) {
        this.email = email_;
    }


    public void setPassword(String password_) {
        this.password = password_;
    }

    public long getId() {
        return id;
    }

    public List<Device> getUserDevices() {
        return userDevices;
    }

    public void setUserDevices(List<Device> userDevices) {
        this.userDevices = userDevices;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.EMPTY_LIST;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}