package org.elsys.mcsecurty;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.elsys.http.Api;
import org.elsys.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener  {
    private EditText passwordInput;
    private EditText emailInput;
    private EditText deviceIdInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
         passwordInput = (EditText) findViewById(R.id.RegPassword);
         emailInput = (EditText) findViewById(R.id.RegEmail);
         deviceIdInput = (EditText) findViewById(R.id.RegDeviceId);
        Button registerButton = findViewById(R.id.RegBtn);
        registerButton.setOnClickListener(this);
    }

//500 e internal na servera
    //huu az bqgam vsichko e tochno vij zashto ne poluchavash maila na servera neshto si oburkal tuka s maila
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.RegBtn: {
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
                Intent myIntent = new Intent(v.getContext(), MainActivity.class);
                myIntent.putExtra("isAuthorized",true);
                myIntent.putExtra("DeviceId1", deviceIdInput.getText().toString());
                startActivity(myIntent);
                break;
            }
        }
    }
}

