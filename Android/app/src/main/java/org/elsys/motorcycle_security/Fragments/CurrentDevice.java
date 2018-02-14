package org.elsys.motorcycle_security.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.elsys.motorcycle_security.R;
import org.elsys.motorcycle_security.http.Api;
import org.elsys.motorcycle_security.models.DeviceConfiguration;
import org.elsys.motorcycle_security.models.Globals;
import org.elsys.motorcycle_security.models.GpsCordinates;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentDevice extends Fragment implements View.OnClickListener {
    private TextView deviceIdText;
    private TextView parkingStatusText;
    private TextView timeOutText;
    private TextView stolenText;
    private TextView statusText;

    public CurrentDevice() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_current_device, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        deviceIdText =  getActivity().findViewById(R.id.DeviceIdText);
        parkingStatusText =  getActivity().findViewById(R.id.ParkingStatusText);
        timeOutText =  getActivity().findViewById(R.id.TimeOutText);
        stolenText =  getActivity().findViewById(R.id.StolenText);
        statusText =  getActivity().findViewById(R.id.StatusText);
        Button timeOutButton =  getActivity().findViewById(R.id.ChangeTimeOutBtn);
        timeOutButton.setOnClickListener(this);
        deviceIdText.setText("Device pin number:" + Globals.deviceInUse);
        final Api api = Api.RetrofitInstance.create();
        api.getDeviceConfiguration(Globals.authorization, Globals.deviceInUse).enqueue(new Callback<DeviceConfiguration>() {
            @Override
            public void onResponse(Call<DeviceConfiguration> call, Response<DeviceConfiguration> response) {
                if(response.isSuccessful()){
                    DeviceConfiguration deviceConfiguration = response.body();
                    if(deviceConfiguration.isParked()) parkingStatusText.setText("Vehicle is parked:" + "ON");
                    if(!deviceConfiguration.isParked()) parkingStatusText.setText("Vehicle is parked:" + "OFF");
                    if(deviceConfiguration.isStolen()) stolenText.setText("Stolen:" + "Yes");
                    if(!deviceConfiguration.isStolen()) stolenText.setText("Stolen:" + "No");
                    timeOutText.setText("GPS Sending frequency:" + String.valueOf(deviceConfiguration.getTimeOut() / 1000)  + " seconds");
                    Log.d("TEXT", timeOutText.getText().toString());
                }
            }
            @Override
            public void onFailure(Call<DeviceConfiguration> call, Throwable t) {
            }
        });
        api.getGPSCoordinates(Globals.authorization, Globals.deviceInUse).enqueue(new Callback<GpsCordinates>() {
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
            case R.id.ChangeTimeOutBtn: {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new ChangeTimeOut());
                ft.commit();
            }
        }
    }
}
