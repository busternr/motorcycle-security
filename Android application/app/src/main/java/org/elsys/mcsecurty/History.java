package org.elsys.mcsecurty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.text.SimpleDateFormat;

public class History extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Long Date = System.currentTimeMillis();
        //SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy\nhh-mm-ss a"); //With hour and seconds
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
        String[] DaysStr = new String[6];
        Long[] DaysLong = new Long[6];

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Button Day1Button =  findViewById(R.id.Day1Btn);
        Button Day2Button =  findViewById(R.id.Day2Btn);
        Button Day3Button =  findViewById(R.id.Day3Btn);
        Button Day4Button =  findViewById(R.id.Day4Btn);
        Button Day5Button =  findViewById(R.id.Day5Btn);

        Day1Button.setOnClickListener(this);
        Day2Button.setOnClickListener(this);
        Day3Button.setOnClickListener(this);
        Day4Button.setOnClickListener(this);
        Day5Button.setOnClickListener(this);

        for(int i=1;i<=5;i++)
        {
            DaysLong[i] = Date - 86400000*i;
            DaysStr[i] = sdf.format(DaysLong[i]);
        }
        Day1Button.setText(DaysStr[1]);
        Day2Button.setText(DaysStr[2]);
        Day3Button.setText(DaysStr[3]);
        Day4Button.setText(DaysStr[4]);
        Day5Button.setText(DaysStr[5]);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Day1Btn:
            {
                Intent myIntent = new Intent(v.getContext(),HistoryForDifferentDays.class);
                myIntent.putExtra("Day","1");
                startActivity(myIntent);
                break;
            }
            case R.id.Day2Btn:
            {
                Intent myIntent = new Intent(v.getContext(),HistoryForDifferentDays.class);
                myIntent.putExtra("Day","2");
                startActivity(myIntent);
                break;
            }
            case R.id.Day3Btn:
            {
                Intent myIntent = new Intent(v.getContext(),HistoryForDifferentDays.class);
                myIntent.putExtra("Day","3");
                startActivity(myIntent);
                break;
            }
            case R.id.Day4Btn:
            {
                Intent myIntent = new Intent(v.getContext(),HistoryForDifferentDays.class);
                myIntent.putExtra("Day","4");
                startActivity(myIntent);
                break;
            }
            case R.id.Day5Btn:
            {
                Intent myIntent = new Intent(v.getContext(),HistoryForDifferentDays.class);
                myIntent.putExtra("Day","5");
                startActivity(myIntent);
                break;
            }
        }
    }
}
