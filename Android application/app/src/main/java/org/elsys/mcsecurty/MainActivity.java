package org.elsys.mcsecurty;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.Toast;

        import org.elsys.http.Api;

        import java.util.ArrayList;
        import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean ParkedStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean isAuthorized = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isAuthorized", false);
        if (!isAuthorized) {
            Intent myIntent = new Intent(this,LoginRegisterActivity.class);
            startActivity(myIntent);
        }
        Button locationButton =  findViewById(R.id.LocBtn);
        Button parkButton =  findViewById(R.id.ParkBtn);
        locationButton.setOnClickListener(this);
        parkButton.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.LocBtn: {
                Intent myIntent = new Intent(v.getContext(),CurrentLocation.class);
                startActivity(myIntent);
                break;
            }
            case R.id.HistoryBtn: {
                Intent myIntent = new Intent(v.getContext(),History.class);
                startActivity(myIntent);
                break;
            }
            case R.id.ParkBtn: {
                if(ParkedStatus == false) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Parked mode ON", Toast.LENGTH_LONG);
                    toast.show();
                    ParkedStatus = true;
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Parked mode OFF", Toast.LENGTH_LONG);
                    toast.show();
                    ParkedStatus = false;
                }
                /*Api api = Api.RetrofitInstance.create();
                api.getGpsCordinates(deviceId).enqueue(new Callback<GpsCordinates>() {
                api.createUserAccount(user).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                    }
                });*/
                break;
            }
        }
    }

}
