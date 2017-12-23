package org.elsys.http;

import org.elsys.models.Device;
import org.elsys.models.GpsCordinates;
import org.elsys.models.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface Api {
    @PUT("/client/send/parking-status")
    Call<User> updateParkingStatus(@Query("deviceId") long deviceId, @Query("isParked") boolean isParked);

    @PUT("/client/send/timeout")
    Call<User> updateTimeout(@Query("deviceId") long deviceId, @Query("timeout") long timeout);

    @POST("/client/send/create-new-user")
    Call<User> createUserAccount(@Body User user);

    @GET("/client/receive/user-account")
    Call<User> getUserAccount(@Header("email") String email);

    @GET("/client/receive/device")
    Call<Device> getDevice(@Query("deviceId") String deviceId);

    @GET("/client/{id}/receive/gps-cordinates")
    Call<GpsCordinates> getGpsCordinates(@Path("id") long id);

    class RetrofitInstance {
        private static Api service;
        public static Api create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://130.204.140.70:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            service = retrofit.create(Api.class);
            return service;
        }
    }
}