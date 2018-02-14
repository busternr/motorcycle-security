package org.elsys.motorcycle_security.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
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

public class HistoryForDifferentDays extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String day;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        day = getArguments().getString("day");
        return inflater.inflate(R.layout.fragment_history_for_different_days, container, false);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        final float zoomlevel = 18;
        Api api = Api.RetrofitInstance.create();
        api.getGPSCordinatesForDay(Globals.authorization, Globals.deviceInUse, day).enqueue(new Callback<List<GpsCordinates>>() {
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
