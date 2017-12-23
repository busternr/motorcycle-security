package org.elsys.mcsecurty;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.Toast;

        import java.util.ArrayList;
        import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean ParkedStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean isAuthorized = getIntent().getBooleanExtra("isAuthorized", false);
        boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("First time rujsjijn", true);
        if (isFirstRun && !isAuthorized)
        {
            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("First time run", false).apply();
            Intent myIntent = new Intent(this,LoginRegisterActivity.class);
            startActivity(myIntent);
        }

        Button locationButton =  findViewById(R.id.LocBtn);
        Button parkButton =  findViewById(R.id.ParkBtn);
        locationButton.setOnClickListener(this);
        parkButton.setOnClickListener(this);
        int counter  = 1;
        while(counter <= 5)
        {
            String deviceId = getIntent().getStringExtra("DeviceId" + counter);
            if(deviceId == null) break;
            GlobalVariables.userDevices.add(deviceId);
            counter++;
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.LocBtn:
            {
                Intent myIntent = new Intent(v.getContext(),CurrentLocation.class);
                startActivity(myIntent);
                break;
            }
            case R.id.HistoryBtn:
            {
                Intent myIntent = new Intent(v.getContext(),History.class);
                startActivity(myIntent);
                break;
            }
            case R.id.ParkBtn:
            {
                if(ParkedStatus == false)
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Parked mode ON", Toast.LENGTH_LONG);
                    toast.show();
                    ParkedStatus = true;
                }
                else
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Parked mode OFF", Toast.LENGTH_LONG);
                    toast.show();
                    ParkedStatus = false;
                }
                break;
            }
        }
    }

}
