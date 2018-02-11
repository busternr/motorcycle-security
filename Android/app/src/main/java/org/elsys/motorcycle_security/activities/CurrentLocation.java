package org.elsys.motorcycle_security.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.elsys.motorcycle_security.R;
import org.elsys.motorcycle_security.http.Api;
import org.elsys.motorcycle_security.models.Device;
import org.elsys.motorcycle_security.models.DeviceConfiguration;
import org.elsys.motorcycle_security.models.Globals;
import org.elsys.motorcycle_security.models.GpsCordinates;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentLocation extends FragmentActivity implements OnMapReadyCallback {
    private int counter;
    private GoogleMap mMap;
    private Marker deviceInUseMarker;
    private Button notStolenButton;
    private int countOfRequests = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        notStolenButton =  findViewById(R.id.NotStolenBtn);
        notStolenButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Api api = Api.RetrofitInstance.create();
                DeviceConfiguration deviceConfiguration = new DeviceConfiguration();
                deviceConfiguration.setDeviceId(Globals.deviceInUse);
                deviceConfiguration.setStolen(false);
                api.updateStolenStatus(Globals.authorization, deviceConfiguration).enqueue(new Callback<DeviceConfiguration>() {
                    @Override
                    public void onResponse(Call<DeviceConfiguration> call, Response<DeviceConfiguration> response) {}
                    @Override
                    public void onFailure(Call<DeviceConfiguration> call, Throwable t) {}
                });
                Intent myIntent = new Intent(v.getContext(),Main.class);
                startActivity(myIntent);
            }
        });
        notStolenButton.setVisibility(View.GONE);
    }

    final Handler handler = new Handler();
    Timer timer = new Timer(false);
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    updatePos();
                }
            });
        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        final float zoomlevel = 18;
        int numberOfUserDevices = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getInt("Number of devices", 1);
        final Api api = Api.RetrofitInstance.create();
        for (counter = 0; counter <= numberOfUserDevices; counter++) {
            final String deviceId = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("Device " + counter ,"");
            api.getGPSCordinates(Globals.authorization, deviceId).enqueue(new Callback<GpsCordinates>() {
                @Override
                public void onResponse(Call<GpsCordinates> call, Response<GpsCordinates> response) {
                    if (response.isSuccessful()) {
                        GpsCordinates gpsCordinates = response.body();
                        LatLng currLocation = new LatLng(gpsCordinates.getX(), gpsCordinates.getY());
                        if(deviceId.matches(Globals.deviceInUse)) deviceInUseMarker = mMap.addMarker(new MarkerOptions().position(currLocation).title(deviceId));
                        else mMap.addMarker(new MarkerOptions().position(currLocation).title(deviceId));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currLocation, zoomlevel));
                    }
                }
                @Override
                public void onFailure(Call<GpsCordinates> call, Throwable t) {
                }
            });
        }
        api.getDeviceConfiguration(Globals.authorization, Globals.deviceInUse).enqueue(new Callback<DeviceConfiguration>() {
            @Override
            public void onResponse(Call<DeviceConfiguration> call, Response<DeviceConfiguration> response) {
                if (response.isSuccessful()) {
                    DeviceConfiguration deviceConfiguration = response.body();
                    Globals.isStolen = deviceConfiguration.isStolen();
                    if(deviceConfiguration.isStolen()) {
                        notStolenButton.setVisibility(View.VISIBLE);
                        timer.schedule(timerTask, 1000, 10000);
                    }
                }
            }
            @Override
            public void onFailure(Call<DeviceConfiguration> call, Throwable t) {
            }
        });
    }
    void updatePos() {
        final float zoomlevel = 18;
        final Api api = Api.RetrofitInstance.create();
        api.getGPSCordinates(Globals.authorization, Globals.deviceInUse).enqueue(new Callback<GpsCordinates>() {
            @Override
            public void onResponse(Call<GpsCordinates> call, Response<GpsCordinates> response) {
                if (response.isSuccessful()) {
                    if(countOfRequests >= 10) {
                        countOfRequests = 0;
                        api.getDeviceConfiguration(Globals.authorization, Globals.deviceInUse).enqueue(new Callback<DeviceConfiguration>() {
                            @Override
                            public void onResponse(Call<DeviceConfiguration> call, Response<DeviceConfiguration> response) {
                                if (response.isSuccessful()) {
                                    DeviceConfiguration deviceConfiguration = response.body();
                                    Globals.isStolen = deviceConfiguration.isStolen();
                                }
                            }
                            @Override
                            public void onFailure(Call<DeviceConfiguration> call, Throwable t) {
                            }
                        });
                    }
                    if(Globals.isStolen == false) {
                        DeviceConfiguration deviceConfiguration = new DeviceConfiguration();
                        deviceConfiguration.setDeviceId(Globals.deviceInUse);
                        deviceConfiguration.setStolen(false);
                        api.updateStolenStatus(Globals.authorization, deviceConfiguration).enqueue(new Callback<DeviceConfiguration>() {
                            @Override
                            public void onResponse(Call<DeviceConfiguration> call, Response<DeviceConfiguration> response) {}
                            @Override
                            public void onFailure(Call<DeviceConfiguration> call, Throwable t) {}
                        });
                        DeviceConfiguration deviceConfiguration2 = new DeviceConfiguration();
                        deviceConfiguration2.setDeviceId(Globals.deviceInUse);
                        deviceConfiguration2.setTimeOut(150000);
                        api.updateTimeOut(Globals.authorization, deviceConfiguration2).enqueue(new Callback<DeviceConfiguration>() {
                            @Override
                            public void onResponse(Call<DeviceConfiguration> call, Response<DeviceConfiguration> response) {}
                            @Override
                            public void onFailure(Call<DeviceConfiguration> call, Throwable t) {}
                        });
                        timer.cancel();
                    }
                    countOfRequests++;
                    GpsCordinates gpsCordinates = response.body();
                    LatLng currLocation = new LatLng(gpsCordinates.getX(), gpsCordinates.getY());
                    deviceInUseMarker.remove();
                    deviceInUseMarker = mMap.addMarker(new MarkerOptions().position(currLocation).title(Globals.deviceInUse + " speed:" + Double.toString(gpsCordinates.getSpeed()) + "km/h"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currLocation, zoomlevel));
                }
            }
            @Override
            public void onFailure(Call<GpsCordinates> call, Throwable t) {
            }
        });
    }
}