package org.elsys.motorcycle_security.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.elsys.motorcycle_security.R;
import org.elsys.motorcycle_security.http.Api;
import org.elsys.motorcycle_security.models.DeviceConfiguration;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetCurrentDevice extends AppCompatActivity implements View.OnClickListener {
    private TextView deviceIdText;
    private TextView parkingStatusText;
    private TextView timeOutText;
    String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_current_device);
        deviceId = getIntent().getStringExtra("DeviceId");
        deviceIdText = findViewById(R.id.DeviceIdText2);
        parkingStatusText = findViewById(R.id.ParkingStatusText2);
        timeOutText = findViewById(R.id.TimeOutText2);
        Button setCurrentDeviceButton = findViewById(R.id.SetCurrentDeviceBtn);
        setCurrentDeviceButton.setOnClickListener(this);
        deviceIdText.setText("Device pin number:" + deviceId);
        Api api = Api.RetrofitInstance.create();
        api.getDeviceConfiguration(deviceId).enqueue(new Callback<DeviceConfiguration>() {
            @Override
            public void onResponse(Call<DeviceConfiguration> call, Response<DeviceConfiguration> response) {
                DeviceConfiguration deviceConfiguration = response.body();
                if(deviceConfiguration.isParked()) parkingStatusText.setText("Vehicle is parked:" + "ON");
                else if(!deviceConfiguration.isParked()) parkingStatusText.setText("Vehicle is parked:" + "OFF");
                timeOutText.setText("GPS Sending frequency:" + String.valueOf(deviceConfiguration.getTimeOut()) + " mileseconds");
            }
            @Override
            public void onFailure(Call<DeviceConfiguration> call, Throwable t) {}
        });
    }

    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.SetCurrentDeviceBtn: {
                getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putString("Current device in use", deviceId).apply();
                Intent myIntent = new Intent(v.getContext(),Settings.class);
                startActivity(myIntent);
            }
        }
    }
}
