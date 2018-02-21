package org.elsys.motorcycle_security.Fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.elsys.motorcycle_security.R;
import org.elsys.motorcycle_security.http.Api;
import org.elsys.motorcycle_security.models.DeviceConfiguration;
import org.elsys.motorcycle_security.models.Globals;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeTimeOut extends Fragment implements View.OnClickListener {
    private EditText timeOutInput;
    private TextView errorsText;


    public ChangeTimeOut() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_time_out, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        View fragmentMap = getActivity().findViewById(R.id.map);
        fragmentMap.setVisibility(View.GONE);
        timeOutInput = getActivity().findViewById(R.id.TimeOutInput);
        errorsText = getActivity().findViewById(R.id.ErrorsChangeTimeoutText);
        Button timeOutButton = getActivity().findViewById(R.id.ChangeTimeOutBtn2);
        timeOutButton.setOnClickListener(this);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.ChangeTimeOutBtn2: {
                if (timeOutInput.getText().toString().length() == 0)
                    errorsText.setText("Seconds field can't be blank");
                else {
                    if (Long.valueOf(timeOutInput.getText().toString()) < 15 || Long.valueOf(timeOutInput.getText().toString()) > 600)
                        errorsText.setText("Seconds must be between 15 seconds and 10 minutes");
                    else {
                        Api api = Api.RetrofitInstance.create();
                        long seconds = Long.valueOf(timeOutInput.getText().toString()) * 1000;
                        DeviceConfiguration deviceConfiguration = new DeviceConfiguration();
                        deviceConfiguration.setDeviceId(Globals.deviceInUse);
                        deviceConfiguration.setTimeOut(seconds);
                        api.updateTimeOut(Globals.authorization, deviceConfiguration).enqueue(new Callback<DeviceConfiguration>() {
                            @Override
                            public void onResponse(Call<DeviceConfiguration> call, Response<DeviceConfiguration> response) {
                            }

                            @Override
                            public void onFailure(Call<DeviceConfiguration> call, Throwable t) {
                            }
                        });
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, new CurrentDevice());
                        ft.commit();
                    }
                }
            }
        }
    }
}
