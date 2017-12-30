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
        String[] daysStr = new String[6];
        Long[] daysLong = new Long[6];

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Button day1Button =  findViewById(R.id.Day1Btn);
        Button day2Button =  findViewById(R.id.Day2Btn);
        Button day3Button =  findViewById(R.id.Day3Btn);
        Button day4Button =  findViewById(R.id.Day4Btn);
        Button day5Button =  findViewById(R.id.Day5Btn);

        day1Button.setOnClickListener(this);
        day2Button.setOnClickListener(this);
        day3Button.setOnClickListener(this);
        day4Button.setOnClickListener(this);
        day5Button.setOnClickListener(this);

        for(int i=1;i<=5;i++)
        {
            daysLong[i] = Date - 86400000*i;
            daysStr[i] = sdf.format(daysLong[i]);
        }
        day1Button.setText(daysStr[1]);
        day2Button.setText(daysStr[2]);
        day3Button.setText(daysStr[3]);
        day4Button.setText(daysStr[4]);
        day5Button.setText(daysStr[5]);
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
