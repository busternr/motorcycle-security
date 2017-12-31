package org.elsys.motorcycle_security.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.elsys.motorcycle_security.R;
import org.elsys.motorcycle_security.http.Api;
import org.elsys.motorcycle_security.models.DeviceConfiguration;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeTimeOut extends AppCompatActivity implements View.OnClickListener {
    private EditText timeOutInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_time_out);
        timeOutInput = findViewById(R.id.TimeOutInput);
        Button timeOutButton = findViewById(R.id.ChangeTimeOutBtn2);
        timeOutButton.setOnClickListener(this);
    }

    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.ChangeTimeOutBtn2: {
                String deviceInUse = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("Current device in use", "");
                Api api = Api.RetrofitInstance.create();
                api.updateTimeOut(deviceInUse, Long.valueOf(timeOutInput.getText().toString())).enqueue(new Callback<DeviceConfiguration>() {
                    @Override
                    public void onResponse(Call<DeviceConfiguration> call, Response<DeviceConfiguration> response) {}
                    @Override
                    public void onFailure(Call<DeviceConfiguration> call, Throwable t) {}
                });
                Intent myIntent = new Intent(v.getContext(),CurrentDevice.class);
                startActivity(myIntent);
            }
        }
    }
}
