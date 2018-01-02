package org.elsys.motorcycle_security.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.elsys.motorcycle_security.R;

public class AllDevices extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
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

       /* for(int i=1;i<=5;i++)
        {
            daysLong[i] = Date - 86400000*i;
            daysStr[i] = sdf.format(daysLong[i]);
        }
        day1Button.setText(daysStr[1]);
        day2Button.setText(daysStr[2]);
        day3Button.setText(daysStr[3]);
        day4Button.setText(daysStr[4]);
        day5Button.setText(daysStr[5]);*/
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Device1Btn:
            {
                break;
            }
            case R.id.Device2Btn:
            {
                break;
            }
            case R.id.Device3Btn:
            {
                break;
            }
            case R.id.Device4Btn:
            {
                break;
            }
            case R.id.Device5Btn:
            {
                break;
            }
        }
    }
}