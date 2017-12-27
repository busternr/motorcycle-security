package org.elsys.mcsecurty;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.elsys.http.Api;
import org.elsys.models.GpsCordinates;
import org.elsys.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener  {
    private EditText passwordInput;
    private EditText emailInput;
    private TextView errorsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        passwordInput = (EditText) findViewById(R.id.LoginPassword);
        emailInput = (EditText) findViewById(R.id.LoginEmail);
        errorsText = (TextView) findViewById(R.id.ErrorsLoginText);
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
                                    int numberOfDevices =  getSharedPreferences("PREFERENCE", MODE_PRIVATE).getInt("Number of devices", numberOfDevices);
                                    for(int counter=1;counter<=numberOfDevices; counter++)
                                    {
                                        String deviceId = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("Device " + counter , deviceId);
                                        Log.d("DEVICEID:", deviceId);
                                        Log.d("ADDING DEVICE TO LIST:", "Device " + counter);
                                        GlobalVariables.userDevices.add(deviceId);
                                    }
                                    Intent myIntent = new Intent(v.getContext(), MainActivity.class);
                                    myIntent.putExtra("isAuthorized", true);
                                    startActivity(myIntent);
                                }
                                else errorsText.setText("Email or password doesn't match.");
                            } else {
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
