package org.elsys.motorcycle_security.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.elsys.motorcycle_security.R;

public class AllDevices extends AppCompatActivity implements View.OnClickListener {
    String[] device = new String[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_devices);
        Button device1Button =  findViewById(R.id.Device1Btn);
        Button device2Button =  findViewById(R.id.Device2Btn);
        Button device3Button =  findViewById(R.id.Device3Btn);
        Button device4Button =  findViewById(R.id.Device4Btn);
        Button device5Button =  findViewById(R.id.Device5Btn);
        device1Button.setOnClickListener(this);
        device2Button.setOnClickListener(this);
        device3Button.setOnClickListener(this);
        device4Button.setOnClickListener(this);
        device5Button.setOnClickListener(this);
        int devices = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getInt("Number of devices", 1);
        for(int i=1;i<=5;i++)
        {
            device1Button.setVisibility(View.GONE);
            device2Button.setVisibility(View.GONE);
            device3Button.setVisibility(View.GONE);
            device4Button.setVisibility(View.GONE);
            device5Button.setVisibility(View.GONE);

        }
       for(int i=0;i<=devices;i++)
       {
            device[i] = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("Device " + i, "");
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
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Device1Btn:
            {
                Intent myIntent = new Intent(v.getContext(),SetCurrentDevice.class);
                myIntent.putExtra("DeviceId", device[0]);
                startActivity(myIntent);
                break;
            }
            case R.id.Device2Btn:
            {
                Intent myIntent = new Intent(v.getContext(),SetCurrentDevice.class);
                myIntent.putExtra("DeviceId", device[1]);
                startActivity(myIntent);
                break;
            }
            case R.id.Device3Btn:
            {
                Intent myIntent = new Intent(v.getContext(),SetCurrentDevice.class);
                myIntent.putExtra("DeviceId", device[2]);
                startActivity(myIntent);
                break;
            }
            case R.id.Device4Btn:
            {
                Intent myIntent = new Intent(v.getContext(),SetCurrentDevice.class);
                myIntent.putExtra("DeviceId", device[3]);
                startActivity(myIntent);
                break;
            }
            case R.id.Device5Btn:
            {
                Intent myIntent = new Intent(v.getContext(),SetCurrentDevice.class);
                myIntent.putExtra("DeviceId", device[4]);
                startActivity(myIntent);
                break;
            }
        }
    }
}