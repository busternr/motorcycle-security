package org.elsys.motorcycle_security.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class ChangeTimeOut extends AppCompatActivity implements View.OnClickListener {
    private EditText timeOutInput;
    private TextView errorsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_time_out);
        timeOutInput = findViewById(R.id.TimeOutInput);
        errorsText = findViewById(R.id.ErrorsChangeTimeoutText);
        Button timeOutButton = findViewById(R.id.ChangeTimeOutBtn2);
        timeOutButton.setOnClickListener(this);
    }

    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.ChangeTimeOutBtn2: {
                if (timeOutInput.getText().toString().length() == 0)
                    errorsText.setText("Seconds field can't be blank");
                else {
                    if (Long.valueOf(timeOutInput.getText().toString()) < 15000 || Long.valueOf(timeOutInput.getText().toString()) > 600000)
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
                        Intent myIntent = new Intent(v.getContext(), CurrentDevice.class);
                        startActivity(myIntent);
                    }
                }
            }
        }
    }
}
