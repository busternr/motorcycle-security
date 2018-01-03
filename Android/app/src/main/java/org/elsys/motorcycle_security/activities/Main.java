package org.elsys.motorcycle_security.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.elsys.motorcycle_security.R;
import org.elsys.motorcycle_security.http.Api;
import org.elsys.motorcycle_security.models.DeviceConfiguration;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main extends AppCompatActivity implements View.OnClickListener {

    private boolean ParkedStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean isAuthorized = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isAuthorized", false);
        if (!isAuthorized) {
            Intent myIntent = new Intent(this,LoginRegister.class);
            startActivity(myIntent);
        }
        Button locationButton =  findViewById(R.id.LocBtn);
        Button parkButton =  findViewById(R.id.ParkBtn);
        Button historyButton =  findViewById(R.id.HistoryBtn);
        Button settingsButton =  findViewById(R.id.SettingsBtn);
        locationButton.setOnClickListener(this);
        parkButton.setOnClickListener(this);
        historyButton.setOnClickListener(this);
        settingsButton.setOnClickListener(this);
    }

    public void onClick(View v) {
        String deviceInUse = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("Current device in use", "");
        switch (v.getId()) {
            case R.id.LocBtn: {
                Intent myIntent = new Intent(v.getContext(),CurrentLocation.class);
                startActivity(myIntent);
                break;
            }
            case R.id.HistoryBtn: {
                Intent myIntent = new Intent(v.getContext(),History.class);
                startActivity(myIntent);
                break;
            }
            case R.id.ParkBtn: {
                if(ParkedStatus == false) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Parked mode ON", Toast.LENGTH_LONG);
                    toast.show();
                    ParkedStatus = true;
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Parked mode OFF", Toast.LENGTH_LONG);
                    toast.show();
                    ParkedStatus = false;
                }
                Api api = Api.RetrofitInstance.create();
                api.updateParkingStatus(deviceInUse,ParkedStatus).enqueue(new Callback<DeviceConfiguration>() {
                    @Override
                    public void onResponse(Call<DeviceConfiguration> call, Response<DeviceConfiguration> response) {}
                    @Override
                    public void onFailure(Call<DeviceConfiguration> call, Throwable t) {}
                });
                break;
            }
            case R.id.SettingsBtn: {
                Intent myIntent = new Intent(v.getContext(),Settings.class);
                startActivity(myIntent);
                break;
            }
        }
    }

}
