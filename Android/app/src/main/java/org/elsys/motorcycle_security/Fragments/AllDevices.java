package org.elsys.motorcycle_security.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.elsys.motorcycle_security.R;

import java.util.ArrayList;
import java.util.List;

public class AllDevices extends Fragment implements View.OnClickListener {
    //    String[] device = new String[6];
    String[] device = {"","","","","",""};
    public AllDevices() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_devices_list_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        View fragmentMap = getActivity().findViewById(R.id.map);
        fragmentMap.setVisibility(View.INVISIBLE);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE);
        int devices = sharedPreferences.getInt("Number of devices", 0);
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
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Fragment fragment = new SetCurrentDevice();
                Bundle bundle=new Bundle();
                bundle.putString("deviceId", device[position]);
                fragment.setArguments(bundle);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}