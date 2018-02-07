package org.elsys.motorcycle_security.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.elsys.motorcycle_security.R;
import org.elsys.motorcycle_security.http.Api;
import org.elsys.motorcycle_security.models.Device;
import org.elsys.motorcycle_security.models.Globals;
import org.elsys.motorcycle_security.models.LoginDetails;
import org.elsys.motorcycle_security.models.User;

import java.util.List;

import okhttp3.Headers;
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
                    final Api api = Api.RetrofitInstance.create();
                    final LoginDetails loginDetails = new LoginDetails(emailInput.getText().toString(), passwordInput.getText().toString());
                    api.Login(loginDetails).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.isSuccessful()) {
                                String token = response.headers().get("authorization");
                                getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putString("Authorization", token).apply();
                            }
                            else errorsText.setText("Email or password doesn't match.");
                            api.getUserAccount(emailInput.getText().toString(), getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("Authorization", "")).enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    if (response.isSuccessful()) {
                                        User user = response.body();
                                        List<Device> userDevices = user.getDevices();
                                        for (int counter = 0; counter < userDevices.size(); counter++) {
                                            String deviceId = user.getDevices().get(counter).getDeviceId();
                                            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putString("Device " + counter, deviceId).apply();
                                            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putInt("Number of devices", counter).apply();
                                            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putString("Current device in use", deviceId).apply();
                                        }
                                        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isAuthorized", true).apply();
                                        Intent myIntent = new Intent(v.getContext(), Main.class);
                                        startActivity(myIntent);
                                    }
                                }
                                @Override
                                public void onFailure(Call<User> call, Throwable t) {}
                            });
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {}
                    });
                }
            }
        }
    }
}
