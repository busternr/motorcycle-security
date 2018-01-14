package org.elsys.motorcycle_security.http;

import org.elsys.motorcycle_security.models.Device;
import org.elsys.motorcycle_security.models.DeviceConfiguration;
import org.elsys.motorcycle_security.models.Globals;
import org.elsys.motorcycle_security.models.GpsCordinates;
import org.elsys.motorcycle_security.models.LoginDetails;
import org.elsys.motorcycle_security.models.User;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface Api {
    @PUT("/client/send/login")
    Call<LoginDetails> Login(@Body LoginDetails loginDetails);

    @PUT("/client/send/parking-status")
    Call<DeviceConfiguration> updateParkingStatus(@Query("deviceId") String deviceId, @Query("isParked") boolean isParked, @Header("authorization") String authorization);

    @PUT("/client/send/timeout")
    Call<DeviceConfiguration> updateTimeOut(@Query("deviceId") String deviceId, @Query("timeout") long timeOut, @Header("authorization") String authorization);

    @POST("/client/send/create-new-user")
    Call<User> createUserAccount(@Body User user);

    @POST("/client/send/create-new-device")
    Call<Device> createDevice(@Body Device device);

    @GET("/client/receive/user-account")
    Call<User> getUserAccount(@Header("email") String email);

    @GET("/client/{deviceId}/receive/device")
    Call<Device> getDevice(@Path("deviceId") String deviceId);

    @GET("/client/{deviceId}/receive/gps-cordinates")
    Call<GpsCordinates> getGPSCordinates(@Path("deviceId") String deviceId, @Header("authorization") String authorization);

    @GET("/device/{deviceId}/receive/device-configuration")
    Call<DeviceConfiguration> getDeviceConfiguration(@Path("deviceId") String deviceId);

    //String API_HOST = "http://10.0.2.2";  //localhost connection
    //String API_HOST = "http://10.19.9.85"; //Dreamix server
    String API_HOST = "http://130.204.140.70"; //home pc
    String API_PORT = "8080";

    class RetrofitInstance {
        private static Api service;
        public static Api create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_HOST + ":" + API_PORT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            service = retrofit.create(Api.class);
            return service;
        }
    }
}