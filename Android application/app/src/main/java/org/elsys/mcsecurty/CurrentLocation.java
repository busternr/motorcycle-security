package org.elsys.mcsecurty;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.elsys.http.Api;
import org.elsys.models.Device;
import org.elsys.models.GpsCordinates;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentLocation extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private long userDeviceId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        final float zoomlevel = 18;
        Api api = Api.RetrofitInstance.create();
        for (int counter = 0; counter < GlobalVariables.userDevices.size(); counter++) {
            String deviceId = GlobalVariables.userDevices.get(counter);
            System.out.println("------------------------------------------------------------>deviceId" + GlobalVariables.userDevices.get(counter));
            api.getDevice(GlobalVariables.userDevices.get(counter)).enqueue(new Callback<Device>() {
                @Override
                public void onResponse(Call<Device> call, Response<Device> response) {
                    if (response.isSuccessful()) {
                        Device device = response.body();
                        userDeviceId = device.getId();
                        System.out.println("------------------------------------------------------------>userDeviceId" + userDeviceId);
                    }
                }
                @Override
                public void onFailure(Call<Device> call, Throwable t) {
                }
            });
            api.getGpsCordinates(userDeviceId).enqueue(new Callback<GpsCordinates>() {
                @Override
                public void onResponse(Call<GpsCordinates> call, Response<GpsCordinates> response) {
                    if (response.isSuccessful()) {
                        GpsCordinates gpsCordinates = response.body();
                        LatLng CurrLoc = new LatLng(gpsCordinates.getX(), gpsCordinates.getX());
                        System.out.println("X========================================================================" + gpsCordinates.getX());
                        mMap.addMarker(new MarkerOptions().position(CurrLoc).title("Device "));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CurrLoc, zoomlevel));
                    }
                }
                @Override
                public void onFailure(Call<GpsCordinates> call, Throwable t) {
                }
            });
        }
    }
}