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
import org.elsys.models.GpsCordinates;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentLocation extends FragmentActivity implements OnMapReadyCallback {
    private int counter;
    private GoogleMap mMap;
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
        int numberOfUserDevices = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getInt("Number of devices", 1);
        Api api = Api.RetrofitInstance.create();
        for (counter = 0; counter <= numberOfUserDevices; counter++) {
            final String deviceId = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("Device " + counter ,"1");
            api.getGpsCordinates(deviceId).enqueue(new Callback<GpsCordinates>() {
                @Override
                public void onResponse(Call<GpsCordinates> call, Response<GpsCordinates> response) {
                    if (response.isSuccessful()) {
                        GpsCordinates gpsCordinates = response.body();
                        LatLng CurrLoc = new LatLng(gpsCordinates.getX(), gpsCordinates.getX());
                        mMap.addMarker(new MarkerOptions().position(CurrLoc).title(deviceId));
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