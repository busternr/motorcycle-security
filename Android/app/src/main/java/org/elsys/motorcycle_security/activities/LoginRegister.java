package org.elsys.motorcycle_security.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;

import org.elsys.motorcycle_security.R;


public class LoginRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        BootstrapButton RegisterButton =  findViewById(R.id.RegisterBtn);
        BootstrapButton LoginButton =  findViewById(R.id.LoginBtn);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(),Register.class);
                startActivity(myIntent);
            }
        });
        LoginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(),Login.class);
                startActivity(myIntent);
            }
        });
    }
}
