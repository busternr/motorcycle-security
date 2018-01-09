package org.elsys.motorcycle_security.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.elsys.motorcycle_security.R;
import org.elsys.motorcycle_security.http.Api;
import org.elsys.motorcycle_security.models.DeviceConfiguration;
import org.elsys.motorcycle_security.models.Globals;
import org.elsys.motorcycle_security.services.LocationCheckerReceiver;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main extends AppCompatActivity implements View.OnClickListener {

    String deviceInUse;
    private boolean isParked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(!(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)) {
            Toast toast = Toast.makeText(getApplicationContext(), "No internet connection.", Toast.LENGTH_LONG);
            toast.show();
        }
        boolean isAuthorized = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isAuthorized", false);
        boolean justRegistered = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("justRegistered", false);
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
        if(isAuthorized && !justRegistered) {
            Globals.deviceInUse = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("Current device in use", "");
            Log.d("DDDDDD", Globals.deviceInUse);
            Api api = Api.RetrofitInstance.create();
            api.getDeviceConfiguration(Globals.deviceInUse).enqueue(new Callback<DeviceConfiguration>() {
                @Override
                public void onResponse(Call<DeviceConfiguration> call, Response<DeviceConfiguration> response) {
                    DeviceConfiguration deviceConfiguration = response.body();
                    isParked = deviceConfiguration.isParked();
                }
                @Override
                public void onFailure(Call<DeviceConfiguration> call, Throwable t) {
                }
            });
            scheduleLocationCheckerAlarm();
        }
    }

    //Later to be changed !!!
    public void scheduleLocationCheckerAlarm() {
        Intent intent = new Intent(getApplicationContext(), LocationCheckerReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, LocationCheckerReceiver.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long firstMillis = System.currentTimeMillis();
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis, 15000, pIntent);
    }

    public void onClick(View v) {
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
                if(isParked == false) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Parked mode ON", Toast.LENGTH_LONG);
                    toast.show();
                    isParked = true;
                }
                else if(isParked == true) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Parked mode OFF", Toast.LENGTH_LONG);
                    toast.show();
                    isParked = false;
                }
                Api api = Api.RetrofitInstance.create();
                api.updateParkingStatus(deviceInUse,isParked).enqueue(new Callback<DeviceConfiguration>() {
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
