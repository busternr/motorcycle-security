package org.elsys.motorcycle_security.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.elsys.motorcycle_security.R;
import org.elsys.motorcycle_security.http.Api;
import org.elsys.motorcycle_security.models.Globals;
import org.elsys.motorcycle_security.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity implements View.OnClickListener {
    private EditText newPasswordInput;
    private EditText emailInput;
    private TextView errorsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        newPasswordInput = findViewById(R.id.NewPassInput);
        emailInput = findViewById(R.id.EmailInput);
        errorsText = findViewById(R.id.ErrorsChangePassText);
        Button changePasswordButton = findViewById(R.id.ChangePassBtn);
        changePasswordButton.setOnClickListener(this);
    }

    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.ChangePassBtn: {
                if (newPasswordInput.getText().toString().length() == 0) errorsText.setText("New password field can't be blank");
                else if (emailInput.getText().toString().length() == 0) errorsText.setText("Email field can't be blank");
                else if (newPasswordInput.getText().toString().length() < 6) errorsText.setText("New password is too short (Minimum 6 characters)");
                else {
                    final Api api = Api.RetrofitInstance.create();
                    api.getUserAccount(emailInput.getText().toString(), Globals.authorization).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {
                                User user = response.body();
                                if (user.getEmail().equals(emailInput.getText().toString())) {
                                    api.updatePassword(emailInput.getText().toString(), newPasswordInput.getText().toString(), Globals.authorization).enqueue(new Callback<User>() {
                                        @Override
                                        public void onResponse(Call<User> call, Response<User> response) {
                                        }

                                        @Override
                                        public void onFailure(Call<User> call, Throwable t) {
                                        }
                                    });
                                    Toast toast = Toast.makeText(getApplicationContext(), "Password change successful", Toast.LENGTH_LONG);
                                    toast.show();
                                    Intent myIntent = new Intent(v.getContext(), Main.class);
                                    startActivity(myIntent);
                                }
                                else errorsText.setText("Wrong email.");
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
}