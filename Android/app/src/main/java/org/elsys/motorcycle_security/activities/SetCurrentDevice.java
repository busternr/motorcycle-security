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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetCurrentDevice extends AppCompatActivity implements View.OnClickListener {
    private TextView deviceIdText;
    private TextView parkingStatusText;
    private TextView timeOutText;
    private TextView statusText;
    String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_current_device);
        deviceId = getIntent().getStringExtra("DeviceId");
        deviceIdText = findViewById(R.id.DeviceIdText2);
        parkingStatusText = findViewById(R.id.ParkingStatusText2);
        timeOutText = findViewById(R.id.TimeOutText2);
        statusText = findViewById(R.id.StatusText2);
        Button setCurrentDeviceButton = findViewById(R.id.SetCurrentDeviceBtn);
        setCurrentDeviceButton.setOnClickListener(this);
        deviceIdText.setText("Device pin number:" + deviceId);
        Api api;
        api = Api.RetrofitInstance.create();
        api.getDeviceConfiguration(deviceId).enqueue(new Callback<DeviceConfiguration>() {
            @Override
            public void onResponse(Call<DeviceConfiguration> call, Response<DeviceConfiguration> response) {
                DeviceConfiguration deviceConfiguration = response.body();
                if(deviceConfiguration.isParked()) parkingStatusText.setText("Vehicle is parked:" + "ON");
                else if(!deviceConfiguration.isParked()) parkingStatusText.setText("Vehicle is parked:" + "OFF");
                timeOutText.setText("GPS Sending frequency:" + String.valueOf(deviceConfiguration.getTimeOut() / 1000) + " seconds");
            }
            @Override
            public void onFailure(Call<DeviceConfiguration> call, Throwable t) {}
        });
        api = Api.RetrofitInstance.create();
        api.getGPSCordinates(deviceId, Globals.authorization).enqueue(new Callback<GpsCordinates>() {
            @Override
            public void onResponse(Call<GpsCordinates> call, Response<GpsCordinates> response) {
                GpsCordinates gpsCordinates = response.body();
                if(gpsCordinates == null) statusText.setText("Status:" + "No information");
                else {
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
            case R.id.SetCurrentDeviceBtn: {
                Globals.deviceInUse = deviceId;
                Intent myIntent = new Intent(v.getContext(),Settings.class);
                startActivity(myIntent);
            }
        }
    }
}
