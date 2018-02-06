package org.elsys.motorcycle_security.activities;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.elsys.motorcycle_security.R;
import org.elsys.motorcycle_security.http.Api;
import org.elsys.motorcycle_security.models.Device;
import org.elsys.motorcycle_security.models.DeviceConfiguration;
import org.elsys.motorcycle_security.models.Globals;
import org.elsys.motorcycle_security.models.GpsCordinates;
import org.elsys.motorcycle_security.services.LocationCheckerJob;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.job.JobInfo.BACKOFF_POLICY_LINEAR;

public class Main extends AppCompatActivity implements View.OnClickListener {
    private boolean isParked;
    private JobScheduler jobScheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jobScheduler = (JobScheduler) getSystemService(
                Context.JOB_SCHEDULER_SERVICE);
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(!(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)) {
            Toast toast = Toast.makeText(getApplicationContext(), "No internet connection.", Toast.LENGTH_LONG);
            toast.show();
        }
        final boolean isAuthorized = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isAuthorized", false);
        if (!isAuthorized) {
            Intent myIntent = new Intent(this,LoginRegister.class);
            startActivity(myIntent);
        }
        else if(isAuthorized) setGlobals();
        Button locationButton =  findViewById(R.id.LocBtn);
        Button parkButton =  findViewById(R.id.ParkBtn);
        Button historyButton =  findViewById(R.id.HistoryBtn);
        Button settingsButton =  findViewById(R.id.SettingsBtn);
        locationButton.setOnClickListener(this);
        parkButton.setOnClickListener(this);
        historyButton.setOnClickListener(this);
        settingsButton.setOnClickListener(this);
    }

    private void scheduleJob() {
        final ComponentName name = new ComponentName(this, LocationCheckerJob.class);
        JobInfo jobInfo = null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            jobInfo= new JobInfo.Builder(1, name)
                    .setMinimumLatency(1000*5)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .setPersisted(true)
                    .setBackoffCriteria(1000, BACKOFF_POLICY_LINEAR )
                    .build();
        }
        else {
            jobInfo= new JobInfo.Builder(1, name)
                    .setPeriodic(1000*5)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .setPersisted(true)
                    .build();
        }
        jobScheduler.schedule(jobInfo);
    }

    private void setGlobals() {
        Globals.deviceInUse = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("Current device in use", "");
        Globals.authorization = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("Authorization", "");
        Api api = Api.RetrofitInstance.create();
        api.getDeviceConfiguration(Globals.deviceInUse, Globals.authorization).enqueue(new Callback<DeviceConfiguration>() {
            @Override
            public void onResponse(Call<DeviceConfiguration> call, Response<DeviceConfiguration> response) {
                DeviceConfiguration deviceConfiguration = response.body();
                isParked = deviceConfiguration.isParked();
                if(isParked) scheduleJob();
                Globals.isStolen = deviceConfiguration.isStolen();
            }
            @Override
            public void onFailure(Call<DeviceConfiguration> call, Throwable t) {
            }
        });
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
                final Api api = Api.RetrofitInstance.create();
                if(isParked == false) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Parked mode ON", Toast.LENGTH_LONG);
                    toast.show();
                    isParked = true;
                    scheduleJob();
                    api.getGPSCordinates(Globals.deviceInUse, Globals.authorization).enqueue(new Callback<GpsCordinates>() {
                        @Override
                        public void onResponse(Call<GpsCordinates> call, Response<GpsCordinates> response) {
                            if (response.isSuccessful()) {
                                final GpsCordinates gpsCordinates = response.body();
                                api.updateParkedCordinates(Globals.deviceInUse, gpsCordinates.getX(), gpsCordinates.getY(), Globals.authorization).enqueue(new Callback<Device>() {
                                    @Override
                                    public void onResponse(Call<Device> call, Response<Device> response) {}
                                    @Override
                                    public void onFailure(Call<Device> call, Throwable t) {}
                                });
                            }
                        }
                        @Override
                        public void onFailure(Call<GpsCordinates> call, Throwable t) {
                        }
                    });
                }
                else if(isParked == true) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Parked mode OFF", Toast.LENGTH_LONG);
                    toast.show();
                    isParked = false;
                    jobScheduler.cancelAll();
                }
                api.updateParkingStatus(Globals.deviceInUse,isParked, Globals.authorization).enqueue(new Callback<DeviceConfiguration>() {
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