package org.elsys.motorcycle_security.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.elsys.motorcycle_security.R;
import org.elsys.motorcycle_security.http.Api;
import org.elsys.motorcycle_security.models.DeviceConfiguration;
import org.elsys.motorcycle_security.models.Globals;
import org.elsys.motorcycle_security.models.GpsCordinates;

import java.time.Instant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentDevice extends AppCompatActivity implements View.OnClickListener{
    private TextView deviceIdText;
    private TextView parkingStatusText;
    private TextView timeOutText;
    private TextView statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_device);
        deviceIdText = findViewById(R.id.DeviceIdText);
        parkingStatusText = findViewById(R.id.ParkingStatusText);
        timeOutText = findViewById(R.id.TimeOutText);
        statusText = findViewById(R.id.StatusText);
        Button timeOutButton = findViewById(R.id.ChangeTimeOutBtn);
        timeOutButton.setOnClickListener(this);
        Api api;
        final String deviceInUse = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("Current device in use", "");
        deviceIdText.setText("Device pin number:" + deviceInUse);
        api = Api.RetrofitInstance.create();
        api.getDeviceConfiguration(deviceInUse).enqueue(new Callback<DeviceConfiguration>() {
            @Override
            public void onResponse(Call<DeviceConfiguration> call, Response<DeviceConfiguration> response) {
                if(response.isSuccessful()){
                    DeviceConfiguration deviceConfiguration = response.body();
                    if(deviceConfiguration.isParked()) parkingStatusText.setText("Vehicle is parked:" + "ON");
                    else if(!deviceConfiguration.isParked()) parkingStatusText.setText("Vehicle is parked:" + "OFF");
                    timeOutText.setText("GPS Sending frequency:" + String.valueOf(deviceConfiguration.getTimeOut()) + " mileseconds");
                }
            }
            @Override
            public void onFailure(Call<DeviceConfiguration> call, Throwable t) {
            }
        });
        api = Api.RetrofitInstance.create();
        api.getGPSCordinates(deviceInUse, Globals.authorization).enqueue(new Callback<GpsCordinates>() {
            @Override
            public void onResponse(Call<GpsCordinates> call, Response<GpsCordinates> response) {
                GpsCordinates gpsCordinates = response.body();
                if(gpsCordinates == null) statusText.setText("Status:" + "No information");
                else {
//                    Instant instant = Instant.now();
//                    Long date = instant.getEpochSecond();
                    Long date = System.currentTimeMillis();
                    if (date - gpsCordinates.getTime() < 600000)
                        statusText.setText("Status:" + "Turned ON");
                    else if (date - gpsCordinates.getTime() > 600000)
                        statusText.setText("Status:" + "Turned OFF");
                }
            }
            @Override
            public void onFailure(Call<GpsCordinates> call, Throwable t) {
            }
        });
    }

    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.ChangeTimeOutBtn: {
                Intent myIntent = new Intent(v.getContext(),ChangeTimeOut.class);
                startActivity(myIntent);
            }
        }
    }
}
