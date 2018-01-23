package org.elsys.motorcycle_security.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.elsys.motorcycle_security.R;
import org.elsys.motorcycle_security.http.Api;
import org.elsys.motorcycle_security.models.Device;
import org.elsys.motorcycle_security.models.DevicePin;
import org.elsys.motorcycle_security.models.Globals;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDevice extends AppCompatActivity implements View.OnClickListener {
    private EditText deviceIdInput;
    private TextView errorsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        deviceIdInput = findViewById(R.id.DeviceIdInput);
        errorsText = findViewById(R.id.ErrorsAddDevText);
        Button addDeviceButton = findViewById(R.id.AddDeviceBtn);
        addDeviceButton.setOnClickListener(this);
    }

    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.AddDeviceBtn: {
               final Api api = Api.RetrofitInstance.create();
                api.getDevicePin(deviceIdInput.getText().toString()).enqueue(new Callback<DevicePin>() {
                    @Override
                    public void onResponse(Call<DevicePin> call, Response<DevicePin> response) {
                        if (response.isSuccessful()) {
                            DevicePin devicePin = response.body();
                            if (devicePin.getPin().equals(deviceIdInput.getText().toString())) {
                                long userId = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getLong("UserId", 1);
                                Device device = new Device(deviceIdInput.getText().toString(), userId);
                                api.createDevice(device, Globals.authorization).enqueue(new Callback<Device>() {
                                    @Override
                                    public void onResponse(Call<Device> call, Response<Device> response) {}
                                    @Override
                                    public void onFailure(Call<Device> call, Throwable t) {}
                                });
                                int devices = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getInt("Number of devices", 1);
                                devices++;
                                getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putInt("Number of devices", devices).apply();
                                getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putString("Device " + devices, device.getDeviceId()).apply();
                                getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putString("Current device in use", device.getDeviceId()).apply();
                                Intent myIntent = new Intent(v.getContext(),Settings.class);
                                startActivity(myIntent);
                            }
                        }
                        else  errorsText.setText("Invalid device pin number");
                    }
                    @Override
                    public void onFailure(Call<DevicePin> call, Throwable t) {}
                });
            }
        }
    }
}

