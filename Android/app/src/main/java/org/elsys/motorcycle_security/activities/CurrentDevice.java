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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentDevice extends AppCompatActivity implements View.OnClickListener{
    private TextView deviceIdText;
    private TextView parkingStatusText;
    private TextView timeOutText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_device);
        deviceIdText = findViewById(R.id.DeviceIdText);
        parkingStatusText = findViewById(R.id.ParkingStatusText);
        timeOutText = findViewById(R.id.TimeOutText);
        Button timeOutButton = findViewById(R.id.ChangeTimeOutBtn);
        timeOutButton.setOnClickListener(this);
        String deviceInUse = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("Current device in use", "");
        deviceIdText.setText("Device pin number:" + deviceInUse);
        Api api = Api.RetrofitInstance.create();
        api.getDeviceConfiguration(deviceInUse).enqueue(new Callback<DeviceConfiguration>() {
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
            case R.id.ChangeTimeOutBtn: {
                Intent myIntent = new Intent(v.getContext(),ChangeTimeOut.class);
                startActivity(myIntent);
            }
        }
    }
}
