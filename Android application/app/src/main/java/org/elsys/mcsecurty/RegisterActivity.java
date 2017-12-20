package org.elsys.mcsecurty;

import android.app.LoaderManager.LoaderCallbacks;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;


import org.elsys.http.Api;
import org.elsys.models.GpsCordinates;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RegisterActivity extends AppCompatActivity {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://127.0.0.1:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    Api service = retrofit.create(Api.class);
    service.getGpsCordinates((long)1).enqueue(new Callback<ResponseBody>()
    {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if (response.isSuccessful()) {



        } else {


        }
    }
}

