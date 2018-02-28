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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.elsys.motorcycle_security.R;
import org.elsys.motorcycle_security.activities.Main;
import org.elsys.motorcycle_security.http.Api;
import org.elsys.motorcycle_security.models.Device;
import org.elsys.motorcycle_security.models.DevicePin;
import org.elsys.motorcycle_security.models.Globals;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDevice extends Fragment {
    private EditText deviceIdInput;
    private TextView errorsText;

    public AddDevice() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_device, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        View fragmentMap = getActivity().findViewById(R.id.map);
        fragmentMap.setVisibility(View.INVISIBLE);
        deviceIdInput = getActivity().findViewById(R.id.DeviceIdInput);
        errorsText = getActivity().findViewById(R.id.ErrorsAddDevText);
        Button addDeviceButton = getActivity().findViewById(R.id.AddDeviceBtn);
        addDeviceButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                final SharedPreferences sharedPreferences = getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE);
                int devices = sharedPreferences.getInt("Number of devices", 0);
                if(devices >= 3) errorsText.setText("You cannot add fourth device.");
                else if (deviceIdInput.getText().toString().length() == 0)
                    errorsText.setText("Device pin field can't be blank");
                else {
                    final Api api = Api.RetrofitInstance.create();
                    api.getDevicePin(deviceIdInput.getText().toString()).enqueue(new Callback<DevicePin>() {
                        @Override
                        public void onResponse(Call<DevicePin> call, Response<DevicePin> response) {
                            if (response.isSuccessful()) {
                                DevicePin devicePin = response.body();
                                if (devicePin.getPin().equals(deviceIdInput.getText().toString())) {
                                    long userId = sharedPreferences.getLong("UserId", 1);
                                    final Device device = new Device(deviceIdInput.getText().toString(), userId);
                                    api.createDevice(Globals.authorization, device).enqueue(new Callback<Device>() {
                                        @Override
                                        public void onResponse(Call<Device> call, Response<Device> response) {
                                            if(response.isSuccessful()) {
                                                int devices = sharedPreferences.getInt("Number of devices", 1);
                                                devices++;
                                                sharedPreferences.edit().putInt("Number of devices", devices).apply();
                                                sharedPreferences.edit().putString("Device " + devices, device.getDeviceId()).apply();
                                                sharedPreferences.edit().putString("Current device in use", device.getDeviceId()).apply();
                                                Intent myIntent = new Intent(v.getContext(), Main.class);
                                                startActivity(myIntent);
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Device> call, Throwable t) {
                                            Toast.makeText(getContext(), "Server is not responding, please try again later.", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            } else errorsText.setText("Invalid device pin number");
                        }

                        @Override
                        public void onFailure(Call<DevicePin> call, Throwable t) {
                        }
                    });
                }
            }
        });
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
