package org.elsys.motorcycle_security.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.elsys.motorcycle_security.R;
import org.elsys.motorcycle_security.activities.Main;
import org.elsys.motorcycle_security.http.Api;
import org.elsys.motorcycle_security.models.DeviceConfiguration;
import org.elsys.motorcycle_security.models.Globals;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChangeTimeOut extends Fragment {
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
        timeOutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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
                        api.updateTimeOut(Globals.authorization, deviceConfiguration).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(response.isSuccessful()) {
                                    Toast toast = Toast.makeText(getActivity(), "Change successful", Toast.LENGTH_LONG);
                                    toast.show();
                                    Intent myIntent = new Intent(getContext(), Main.class);
                                    startActivity(myIntent);
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(getContext(), "Server is not responding, please try again later.", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }
        });
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
