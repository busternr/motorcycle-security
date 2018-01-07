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
import org.elsys.motorcycle_security.models.Device;
import org.elsys.motorcycle_security.models.DeviceConfiguration;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentDevice extends AppCompatActivity implements View.OnClickListener{
    private TextView deviceIdText;
    private TextView parkingStatusText;
    private TextView timeOutText;
    private TextView statusText;
    private TextView runningTimeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_device);
        deviceIdText = findViewById(R.id.DeviceIdText);
        parkingStatusText = findViewById(R.id.ParkingStatusText);
        timeOutText = findViewById(R.id.TimeOutText);
        statusText = findViewById(R.id.StatusText);
        runningTimeText = findViewById(R.id.RunningTimeText);
        Button timeOutButton = findViewById(R.id.ChangeTimeOutBtn);
        timeOutButton.setOnClickListener(this);
        Api api;
        String deviceInUse = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("Current device in use", "");
        deviceIdText.setText("Device pin number:" + deviceInUse);
        api = Api.RetrofitInstance.create();
        api.getDeviceConfiguration(deviceInUse).enqueue(new Callback<DeviceConfiguration>() {
            @Override
            public void onResponse(Call<DeviceConfiguration> call, Response<DeviceConfiguration> response) {
                DeviceConfiguration deviceConfiguration = response.body();
                if(deviceConfiguration.isParked()) parkingStatusText.setText("Vehicle is parked:" + "ON");
                else if(!deviceConfiguration.isParked()) parkingStatusText.setText("Vehicle is parked:" + "OFF");
                timeOutText.setText("GPS Sending frequency:" + String.valueOf(deviceConfiguration.getTimeOut()) + " mileseconds");
            }
            @Override
            public void onFailure(Call<DeviceConfiguration> call, Throwable t) {
            }
        });
        api = Api.RetrofitInstance.create();
        api.getDevice(deviceInUse).enqueue(new Callback<Device>() {
            @Override
            public void onResponse(Call<Device> call, Response<Device> response) {
                Device device = response.body();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                runningTimeText.setText("Running time:" + sdf.format(device.getUpTime()) + " hours");
                long millis = device.getUpTime();
                long days = TimeUnit.MILLISECONDS.toDays(millis);
                millis -= TimeUnit.DAYS.toMillis(days);
                long hours = TimeUnit.MILLISECONDS.toHours(millis);
                millis -= TimeUnit.HOURS.toMillis(hours);
                long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
                millis -= TimeUnit.MINUTES.toMillis(minutes);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
                StringBuilder sb = new StringBuilder(64);
                if(days >=1) {
                    sb.append(days);
                    sb.append(" Days ");
                }
                if(hours >=1) {
                    sb.append(hours);
                    sb.append(" Hours ");
                }
                if(minutes >=1) {
                    sb.append(minutes);
                    sb.append(" Minutes ");
                }
                sb.append(seconds);
                sb.append(" Seconds");
                runningTimeText.setText("Running time:" + sb.toString());
            }
            @Override
            public void onFailure(Call<Device> call, Throwable t) {
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
