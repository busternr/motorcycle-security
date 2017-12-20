package org.elsys.mcsecurty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.elsys.http.Api;
import org.elsys.models.GpsCordinates;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);
        Api api = Api.RetrofitInstance.create();
        api.getGpsCordinates(1l).enqueue(new Callback<GpsCordinates>() {
            @Override
            public void onResponse (Call < GpsCordinates > call, Response< GpsCordinates > response){
                if (response.isSuccessful()) {
                    GpsCordinates gpsCordinates = response.body();
                }
                else {


                }
            }

            @Override
            public void onFailure(Call<GpsCordinates> call, Throwable t) {

            }
        });
    }
}

