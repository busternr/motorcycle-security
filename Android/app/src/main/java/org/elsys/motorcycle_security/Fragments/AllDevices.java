package org.elsys.motorcycle_security.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.elsys.motorcycle_security.R;

public class AllDevices extends Fragment implements View.OnClickListener {
    String[] device = new String[6];

    public AllDevices() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_devices, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE);
        Button device1Button =  getActivity().findViewById(R.id.Device1Btn);
        Button device2Button =  getActivity().findViewById(R.id.Device2Btn);
        Button device3Button =  getActivity().findViewById(R.id.Device3Btn);
        Button device4Button =  getActivity().findViewById(R.id.Device4Btn);
        Button device5Button =  getActivity().findViewById(R.id.Device5Btn);
        device1Button.setOnClickListener(this);
        device2Button.setOnClickListener(this);
        device3Button.setOnClickListener(this);
        device4Button.setOnClickListener(this);
        device5Button.setOnClickListener(this);
        int devices = sharedPreferences.getInt("Number of devices", 0);
        device1Button.setVisibility(View.GONE);
        device2Button.setVisibility(View.GONE);
        device3Button.setVisibility(View.GONE);
        device4Button.setVisibility(View.GONE);
        device5Button.setVisibility(View.GONE);
        for(int i=0;i<=devices;i++)
        {
            device[i] = sharedPreferences.getString("Device " + i, "");
            Log.d("DEEEEEEEEEEEEEEEEV ", device[i]);
        }
        if(device[0] != null) {
            device1Button.setVisibility(View.VISIBLE);
            device1Button.setText(device[0]);
        }
        if(device[1] != null) {
            device2Button.setVisibility(View.VISIBLE);
            device2Button.setText(device[1]);
        }
        if(device[2] != null) {
            device3Button.setVisibility(View.VISIBLE);
            device3Button.setText(device[2]);
        }
        if(device[3] != null) {
            device4Button.setVisibility(View.VISIBLE);
            device4Button.setText(device[3]);
        }
        if(device[4] != null) {
            device5Button.setVisibility(View.VISIBLE);
            device5Button.setText(device[4]);
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()) {
            case R.id.Device1Btn:
            {
                fragment = new SetCurrentDevice();
                Bundle bundle=new Bundle();
                bundle.putString("deviceId", device[0]);
                fragment.setArguments(bundle);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
                break;
            }
            case R.id.Device2Btn:
            {
                fragment = new SetCurrentDevice();
                Bundle bundle=new Bundle();
                bundle.putString("deviceId", device[1]);
                fragment.setArguments(bundle);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
                break;
            }
            case R.id.Device3Btn:
            {
                fragment = new SetCurrentDevice();
                Bundle bundle=new Bundle();
                bundle.putString("deviceId", device[2]);
                fragment.setArguments(bundle);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
                break;
            }
            case R.id.Device4Btn:
            {
                fragment = new SetCurrentDevice();
                Bundle bundle=new Bundle();
                bundle.putString("deviceId", device[3]);
                fragment.setArguments(bundle);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
                break;
            }
            case R.id.Device5Btn:
            {
                fragment = new SetCurrentDevice();
                Bundle bundle=new Bundle();
                bundle.putString("deviceId", device[4]);
                fragment.setArguments(bundle);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
                break;
            }
        }
    }
}
