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

    private long userDeviceId = 1;
    private long x;
    private long y;
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
        Api api = Api.RetrofitInstance.create();
        api.getGpsCordinates(userDeviceId).enqueue(new Callback<GpsCordinates>() {
            @Override
            public void onResponse (Call< GpsCordinates > call, Response< GpsCordinates > response){
                if (response.isSuccessful()) {
                    GpsCordinates gpsCordinates = response.body();
                    x = gpsCordinates.getX();
                    y = gpsCordinates.getY();
                }
                else {


                }
            }

            @Override
            public void onFailure(Call<GpsCordinates> call, Throwable t) {

            }
        });
        mMap = googleMap;
        float zoomlevel = 18;
        LatLng CurrLoc = new LatLng(x, y);
        mMap.addMarker(new MarkerOptions().position(CurrLoc).title("Your motorcycle"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CurrLoc,zoomlevel));
    }
}