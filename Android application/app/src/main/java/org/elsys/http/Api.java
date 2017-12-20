package org.elsys.http;

import org.elsys.models.GpsCordinates;
import org.elsys.models.User;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface Api {
    @POST("/client/send/parking-status")
    Call<User> updateParkingStatus(@Field("deviceId") long deviceId);

    @POST("/client/send/timeout")
    Call<User> updateTimeout(@Field("deviceId") long deviceId, @Field("timeout") long timeout);

    @POST("/client/send/create-new-user")
    Call<User> updateTimeout(@Field("userName") String userName, @Field("email") String email, @Field("password") String password, @Field("deviceId") String deviceId);

    @GET("/client/receive/user-account")
    Call<User> getUserAccount(@Header("userName") String userName);

    @GET("/client/receive/{id}/gps-cordinates")
    Call<GpsCordinates> getGpsCordinates(@Path("id") long id);

    class RetrofitInstance {
        private static Api service;
        public static Api create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            service = retrofit.create(Api.class);
            return service;
        }
    }
}