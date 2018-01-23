package org.elsys.motorcycle_security.activities;

import android.content.Context;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.elsys.motorcycle_security.R;
import org.elsys.motorcycle_security.http.Api;
import org.elsys.motorcycle_security.models.Globals;
import org.elsys.motorcycle_security.models.GpsCordinates;

import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryForDifferentDays extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_for_different_days);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        final float zoomlevel = 18;
        final String day = getIntent().getStringExtra("Day");
        Api api = Api.RetrofitInstance.create();
        api.getGPSCordinatesForDay(Globals.deviceInUse, day, Globals.authorization).enqueue(new Callback<List<GpsCordinates>>() {
            @Override
            public void onResponse(Call<List<GpsCordinates>> call, Response<List<GpsCordinates>> response) {
                if (response.isSuccessful()) {
                    List<GpsCordinates> gpsCordinates = response.body();
                    for(int counter = 0; counter < gpsCordinates.size(); counter++) {
                        LatLng currentLocation = new LatLng(gpsCordinates.get(counter).getX(), gpsCordinates.get(counter).getY());
                        SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss");
                        mMap.addMarker(new MarkerOptions().position(currentLocation).title(sdf.format(gpsCordinates.get(counter).getDate())));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, zoomlevel));
                    }
                }
            }
            @Override
            public void onFailure(Call<List<GpsCordinates>> call, Throwable t) {
            }
        });
    }
}
