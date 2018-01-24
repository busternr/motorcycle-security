package org.elsys.motorcycle_security.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.elsys.motorcycle_security.R;

public class Settings extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Button currentDeviceButton =  findViewById(R.id.CurrentDeviceBtn);
        Button allDevicesButton =  findViewById(R.id.AllDevicesBtn);
        Button addDeviceButton =  findViewById(R.id.AddDeviceBtn);
        Button changePasswordButton =  findViewById(R.id.ChangePassBtn);
        Button logoutButton =  findViewById(R.id.LogoutBtn);
        currentDeviceButton.setOnClickListener(this);
        allDevicesButton.setOnClickListener(this);
        addDeviceButton.setOnClickListener(this);
        changePasswordButton.setOnClickListener(this);
        logoutButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.CurrentDeviceBtn:
            {
                Intent myIntent = new Intent(v.getContext(),CurrentDevice.class);
                startActivity(myIntent);
                break;
            }
            case R.id.AllDevicesBtn:
            {
                Intent myIntent = new Intent(v.getContext(),AllDevices.class);
                startActivity(myIntent);
                break;
            }
            case R.id.AddDeviceBtn:
            {
                Intent myIntent = new Intent(v.getContext(),AddDevice.class);
                startActivity(myIntent);
                break;
            }
            case R.id.ChangePassBtn:
            {
                Intent myIntent = new Intent(v.getContext(),ChangePassword.class);
                startActivity(myIntent);
                break;
            }
            case R.id.LogoutBtn:
            {
                getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putInt("Number of devices", 0).apply();
                getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isAuthorized", false).apply();
                getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("justRegistered", false).apply();
                Intent myIntent = new Intent(v.getContext(),Main.class);
                startActivity(myIntent);
                break;
            }
        }
    }
}
