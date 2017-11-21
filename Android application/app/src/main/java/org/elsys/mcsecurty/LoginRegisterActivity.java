package org.elsys.mcsecurty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class LoginRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        Button RegisterButton =  findViewById(R.id.RegisterBtn);
        Button LoginButton =  findViewById(R.id.LoginBtn);

        RegisterButton.setOnClickListener(this);
        LoginButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.RegisterBtn:
            {
                Intent myIntent = new Intent(v.getContext(),RegisterActivity.class);
                startActivity(myIntent);
                break;
            }
            case R.id.LoginBtn:
            {
                Intent myIntent = new Intent(v.getContext(),LoginActivity.class);
                startActivity(myIntent);
                break;
            }
        }
    }
}
