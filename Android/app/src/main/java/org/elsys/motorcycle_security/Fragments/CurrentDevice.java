package org.elsys.motorcycle_security.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;

import org.elsys.motorcycle_security.R;
import org.elsys.motorcycle_security.activities.Main;
import org.elsys.motorcycle_security.http.Api;
import org.elsys.motorcycle_security.models.DeviceConfiguration;
import org.elsys.motorcycle_security.models.Globals;
import org.elsys.motorcycle_security.models.GPSCoordinates;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentDevice extends Fragment {
    private TextView deviceIdText;
    private TextView parkingStatusText;
    private TextView timeOutText;
    private TextView stolenText;
    private TextView statusText;
    String[] device = {"","","","",""};

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
        View fragmentMap = getActivity().findViewById(R.id.map);
        fragmentMap.setVisibility(View.INVISIBLE);
        deviceIdText =  getActivity().findViewById(R.id.DeviceIdText);
        parkingStatusText =  getActivity().findViewById(R.id.ParkingStatusText);
        timeOutText =  getActivity().findViewById(R.id.TimeOutText);
        stolenText =  getActivity().findViewById(R.id.StolenText);
        statusText =  getActivity().findViewById(R.id.StatusText);
        BootstrapButton timeOutButton =  getActivity().findViewById(R.id.ChangeTimeOutBtn);
        timeOutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new ChangeTimeOut());
                ft.commit();
            }
        });
        deviceIdText.setText("Device pin number:" + Globals.deviceInUse);
        final Api api = Api.RetrofitInstance.create();
        api.getDeviceConfiguration(Globals.authorization, Globals.deviceInUse).enqueue(new Callback<DeviceConfiguration>() {
            @Override
            public void onResponse(Call<DeviceConfiguration> call, Response<DeviceConfiguration> response) {
                if(response.isSuccessful()){
                    DeviceConfiguration deviceConfiguration = response.body();
                    if(deviceConfiguration.isParked()) parkingStatusText.setText("Is parked:" + "Yes");
                    if(!deviceConfiguration.isParked()) parkingStatusText.setText("Is parked:" + "No");
                    if(deviceConfiguration.isStolen()) stolenText.setText("Stolen:" + "Yes");
                    if(!deviceConfiguration.isStolen()) stolenText.setText("Stolen:" + "No");
                    timeOutText.setText("GPS frequency:" + String.valueOf(deviceConfiguration.getTimeOut() / 1000)  + " seconds");
                }
            }
            @Override
            public void onFailure(Call<DeviceConfiguration> call, Throwable t) {
                Toast.makeText(getContext(), "Server is not responding, please try again later.", Toast.LENGTH_LONG).show();
            }
        });
        api.getGPSCoordinates(Globals.authorization, Globals.deviceInUse).enqueue(new Callback<GPSCoordinates>() {
            @Override
            public void onResponse(Call<GPSCoordinates> call, Response<GPSCoordinates> response) {
                GPSCoordinates GPSCoordinates = response.body();
                if(GPSCoordinates == null) statusText.setText("Status:" + "No information");
                else {
                    Date date = new Date();
                    long currTimeMills = date.getTime();
                    if (currTimeMills - GPSCoordinates.getDate() < 600000)
                        statusText.setText("Status:" + "Turned ON");
                    else if (currTimeMills - GPSCoordinates.getDate() > 600000)
                        statusText.setText("Status:" + "Turned OFF");
                }
            }
            @Override
            public void onFailure(Call<GPSCoordinates> call, Throwable t) {
            }
        });
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE);
        final int devices = sharedPreferences.getInt("Number of devices", 0);
        for(int i=0;i<=devices;i++) {
            device[i] = sharedPreferences.getString("Device " + i, "");
        }
        String[] filteredDevices = new String[devices + 1];
        for(int i=0;!device[i].isEmpty();i++) {
            filteredDevices[i] = device[i];
        }
        ArrayAdapter adapter = new ArrayAdapter<>(view.getContext(), R.layout.device_item, filteredDevices);
        ListView listView = getActivity().findViewById(R.id.all_devices_list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle("Change current device");
                builder.setMessage(Html.fromHtml("Are you sure you want to change your current device to " + "<b>"+ device[position] +"</b>" + "?", Html.FROM_HTML_MODE_LEGACY));
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE);
                                Globals.deviceInUse = device[position];
                                sharedPreferences.edit().putString("Current device in use", device[position]).apply();
                                Intent myIntent = new Intent(getContext(), Main.class);
                                startActivity(myIntent);
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                /*Fragment fragment = new SetCurrentDevice();
                Bundle bundle=new Bundle();
                bundle.putString("deviceId", device[position]);
                fragment.setArguments(bundle);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();*/
            }
        });
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
