package org.elsys.mcsecurty;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean AlarmStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button LocationButton =  findViewById(R.id.LocBtn);
        Button AlarmButton =  findViewById(R.id.AlarmBtn);
        LocationButton.setOnClickListener(this);
        AlarmButton.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.LocBtn:
            {
                Intent myIntent = new Intent(v.getContext(),LocationActivity.class);
                startActivity(myIntent);
                break;
            }
            case R.id.AlarmBtn:
            {
                if(AlarmStatus == false)
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Alarm turned ON", Toast.LENGTH_LONG);
                    toast.show();
                    AlarmStatus = true;
                }
                else
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Alarm turned OFF", Toast.LENGTH_LONG);
                    toast.show();
                    AlarmStatus = false;
                }
                break;
            }
        }
    }

}
