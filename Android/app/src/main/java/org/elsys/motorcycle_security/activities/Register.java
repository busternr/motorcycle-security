package org.elsys.motorcycle_security.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.elsys.motorcycle_security.R;
import org.elsys.motorcycle_security.http.Api;
import org.elsys.motorcycle_security.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity implements View.OnClickListener  {
    private EditText passwordInput;
    private EditText emailInput;
    private EditText deviceIdInput;
    private TextView errorsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
         passwordInput = (EditText) findViewById(R.id.RegPassword);
         emailInput = (EditText) findViewById(R.id.RegEmail);
         deviceIdInput = (EditText) findViewById(R.id.RegDeviceId);
         errorsText = (TextView) findViewById(R.id.ErrorsRegText);
        Button registerButton = findViewById(R.id.RegBtn);
        registerButton.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.RegBtn: {
                if(deviceIdInput.getText().toString().length() == 0) errorsText.setText("Device pin number field can't be blank");
                else if(emailInput.getText().toString().length() == 0) errorsText.setText("Email field can't be blank");
                else if(passwordInput.getText().toString().length() == 0) errorsText.setText("Password field can't be blank");
                else if(emailInput.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) errorsText.setText("Invalid email address.");
                else if(passwordInput.getText().toString().length() < 6) errorsText.setText("Password is too short (Minumum 6 characters)");
                else if(deviceIdInput.getText().toString().length() < 6) errorsText.setText("Invalid device pin number");
                else {
                    Api api = Api.RetrofitInstance.create();
                    User user = new User(emailInput.getText().toString(), passwordInput.getText().toString(), deviceIdInput.getText().toString());
                    api.createUserAccount(user).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                        }
                    });
                    getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putInt("Number of devices", 1).apply();
                    getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isAuthorized", true).apply();
                    getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putString("Device 1", user.getDevices().get(0).getDeviceId()).apply();
                    Intent myIntent = new Intent(v.getContext(), Main.class);
                    startActivity(myIntent);
                    break;
                }
            }
        }
    }
}

