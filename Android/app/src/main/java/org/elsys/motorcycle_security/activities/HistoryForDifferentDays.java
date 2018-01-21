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
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE );
        if(!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast toast = Toast.makeText(getApplicationContext(), "Turn ON GPS.", Toast.LENGTH_LONG);
            toast.show();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
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
                    for(int counter = 0; counter <= gpsCordinates.size(); counter++) {
                        LatLng CurrLoc = new LatLng(gpsCordinates.get(counter).getX(), gpsCordinates.get(counter).getX());
                        SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss");
                        mMap.addMarker(new MarkerOptions().position(CurrLoc).title(sdf.format(gpsCordinates.get(counter).getDate())));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CurrLoc, zoomlevel));
                    }
                }
            }
            @Override
            public void onFailure(Call<List<GpsCordinates>> call, Throwable t) {
            }
        });
    }
}
