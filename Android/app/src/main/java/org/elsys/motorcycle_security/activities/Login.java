package org.elsys.motorcycle_security.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.elsys.motorcycle_security.R;
import org.elsys.motorcycle_security.http.Api;
import org.elsys.motorcycle_security.models.Device;
import org.elsys.motorcycle_security.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private EditText passwordInput;
    private EditText emailInput;
    private TextView errorsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        passwordInput = findViewById(R.id.LoginPassword);
        emailInput = findViewById(R.id.LoginEmail);
        errorsText = findViewById(R.id.ErrorsLoginText);
        Button loginButton = findViewById(R.id.LoginBtn);
        loginButton.setOnClickListener(this);
    }

    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.LoginBtn: {
                if(emailInput.getText().toString().length() == 0) errorsText.setText("Email field can't be blank");
                else if(passwordInput.getText().toString().length() == 0) errorsText.setText("Password field can't be blank");
                else {
                    Api api = Api.RetrofitInstance.create();
                    api.getUserAccount(emailInput.getText().toString()).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {
                                User user = response.body();
                                if (user.getEmail().equals(emailInput.getText().toString()) && user.getPassword().equals(passwordInput.getText().toString())) {
                                    List<Device> userDevices = user.getDevices();
                                    for(int counter=0;counter<userDevices.size(); counter++)
                                    {
                                        String deviceId = user.getDevices().get(counter).getDeviceId();
                                        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putString("Device " + counter, deviceId).apply();
                                        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putInt("Number of devices", counter).apply();
                                        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putString("Current device in use", deviceId).apply();
                                        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putLong("UserId", user.getId()).apply();
                                    }
                                    getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isAuthorized", true).apply();
                                    Intent myIntent = new Intent(v.getContext(), Main.class);
                                    startActivity(myIntent);
                                }
                                else errorsText.setText("Email or password doesn't match.");
                            }
                            else {
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            errorsText.setText("Email or password doesn't match.");
                        }
                    });
                }
            }
        }
    }
}
