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
import org.elsys.motorcycle_security.models.Globals;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeTimeOut extends AppCompatActivity implements View.OnClickListener {
    private EditText timeOutInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_time_out);
        timeOutInput = findViewById(R.id.OldPassInput);
        Button timeOutButton = findViewById(R.id.ChangeTimeOutBtn2);
        timeOutButton.setOnClickListener(this);
    }

    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.ChangeTimeOutBtn2: {
                Api api = Api.RetrofitInstance.create();
                long seconds = Long.valueOf(timeOutInput.getText().toString()) * 1000;
                api.updateTimeOut(Globals.deviceInUse, seconds, Globals.authorization).enqueue(new Callback<DeviceConfiguration>() {
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
