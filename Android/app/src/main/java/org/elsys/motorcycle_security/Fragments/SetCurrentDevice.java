package org.elsys.motorcycle_security.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.elsys.motorcycle_security.R;
import org.elsys.motorcycle_security.activities.Main;
import org.elsys.motorcycle_security.http.Api;
import org.elsys.motorcycle_security.models.DeviceConfiguration;
import org.elsys.motorcycle_security.models.Globals;
import org.elsys.motorcycle_security.models.GpsCordinates;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class SetCurrentDevice extends Fragment implements View.OnClickListener {
    private TextView deviceIdText;
    private TextView parkingStatusText;
    private TextView timeOutText;
    private TextView stolenText;
    private TextView statusText;
    String deviceId;

    public SetCurrentDevice() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_set_current_device, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        deviceId = getArguments().getString("deviceId");
        deviceIdText = getActivity().findViewById(R.id.DeviceIdText2);
        parkingStatusText = getActivity().findViewById(R.id.ParkingStatusText2);
        timeOutText = getActivity().findViewById(R.id.TimeOutText2);
        stolenText = getActivity().findViewById(R.id.StolenText2);
        statusText = getActivity().findViewById(R.id.StatusText2);
        Button setCurrentDeviceButton = getActivity().findViewById(R.id.SetCurrentDeviceBtn);
        setCurrentDeviceButton.setOnClickListener(this);
        deviceIdText.setText("Device pin number:" + deviceId);
        Api api;
        api = Api.RetrofitInstance.create();
        api.getDeviceConfiguration(Globals.authorization, deviceId).enqueue(new Callback<DeviceConfiguration>() {
            @Override
            public void onResponse(Call<DeviceConfiguration> call, Response<DeviceConfiguration> response) {
                DeviceConfiguration deviceConfiguration = response.body();
                if(deviceConfiguration.isParked()) parkingStatusText.setText("Vehicle is parked:" + "ON");
                if(!deviceConfiguration.isParked()) parkingStatusText.setText("Vehicle is parked:" + "OFF");
                if(deviceConfiguration.isStolen()) stolenText.setText("Stolen:" + "Yes");
                if(!deviceConfiguration.isStolen()) stolenText.setText("Stolen:" + "No");
                timeOutText.setText("GPS Sending frequency:" + String.valueOf(deviceConfiguration.getTimeOut() / 1000) + " seconds");
            }
            @Override
            public void onFailure(Call<DeviceConfiguration> call, Throwable t) {}
        });
        api = Api.RetrofitInstance.create();
        api.getGPSCoordinates(Globals.authorization, deviceId).enqueue(new Callback<GpsCordinates>() {
            @Override
            public void onResponse(Call<GpsCordinates> call, Response<GpsCordinates> response) {
                GpsCordinates gpsCordinates = response.body();
                if(gpsCordinates == null) statusText.setText("Status:" + "No information");
                else {
                    Date date = new Date();
                    long currTimeMills = date.getTime();
                    if (currTimeMills - gpsCordinates.getDate() < 600000)
                        statusText.setText("Status:" + "Turned ON");
                    else if (currTimeMills - gpsCordinates.getDate() > 600000)
                        statusText.setText("Status:" + "Turned OFF");
                }
            }
            @Override
            public void onFailure(Call<GpsCordinates> call, Throwable t) {
            }
        });
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.SetCurrentDeviceBtn: {
                SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE);
                Globals.deviceInUse = deviceId;
                sharedPreferences.edit().putString("Current device in use", deviceId).apply();
                Intent myIntent = new Intent(v.getContext(), Main.class);
                startActivity(myIntent);
            }
        }
    }
}
