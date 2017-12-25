package org.elsys.mcsecurty;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.elsys.http.Api;
import org.elsys.models.GpsCordinates;
import org.elsys.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener  {
    private EditText passwordInput;
    private EditText emailInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        passwordInput = (EditText) findViewById(R.id.LoginPassword);
        emailInput = (EditText) findViewById(R.id.LoginEmail);
        Button loginButton = findViewById(R.id.LoginBtn);
        loginButton.setOnClickListener(this);
    }

    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.LoginBtn: {
                Api api = Api.RetrofitInstance.create();
                api.getUserAccount(emailInput.getText().toString()).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse (Call<User> call, Response<User> response){
                        if (response.isSuccessful()) {
                            User user = response.body();
                            if (user.getEmail().equals(emailInput.getText().toString()) && user.getPassword().equals(passwordInput.getText().toString()))
                            {
                                Intent myIntent = new Intent(v.getContext(), MainActivity.class);
                                myIntent.putExtra("isAuthorized",true);
                                startActivity(myIntent);
                            }
                            else
                            {
                                System.out.println("===================================WRONG PW");
                            }
                        }
                        else {


                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
            }
        }
    }
}
