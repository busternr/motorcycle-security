package org.elsys.motorcycle_security.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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


public class SetRadius extends Fragment {
    private EditText radiusInput;
    private TextView errorsText;


    public SetRadius() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_set_radius, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        View fragmentMap = getActivity().findViewById(R.id.map);
        fragmentMap.setVisibility(View.GONE);
        radiusInput = getActivity().findViewById(R.id.RadiusInput);
        errorsText = getActivity().findViewById(R.id.ErrorsSetRadiustText);
        final Button radiusButton = getActivity().findViewById(R.id.SetRadiusBtn2);
        radiusButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (radiusInput.getText().toString().length() == 0)
                    errorsText.setText("Seconds field can't be blank");
                else {
                    if (Long.valueOf(radiusInput.getText().toString()) < 0 || Long.valueOf(radiusInput.getText().toString()) > 10000)
                        errorsText.setText("Radius must be between 0 and 10000 meters");
                    else {
                        Api api = Api.RetrofitInstance.create(getContext());
                        DeviceConfiguration deviceConfiguration = new DeviceConfiguration();
                        deviceConfiguration.setDeviceId(Globals.deviceInUse);
                        deviceConfiguration.setRadius(Long.valueOf(radiusInput.getText().toString()));
                        api.updateRadius(Globals.authorization, deviceConfiguration).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(response.isSuccessful()) {
                                    Globals.radius = Long.valueOf(radiusInput.getText().toString());
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

    @Override
    public void onResume() {
        super.onResume();
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);
    }
}
