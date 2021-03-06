package org.elsys.motorcycle_security.http;

import android.content.Context;

import org.elsys.motorcycle_security.models.Device;
import org.elsys.motorcycle_security.models.DeviceConfiguration;
import org.elsys.motorcycle_security.models.DevicePin;
import org.elsys.motorcycle_security.models.GPSCoordinates;
import org.elsys.motorcycle_security.models.LoginDetails;
import org.elsys.motorcycle_security.models.User;

import java.util.List;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface Api {
    @PUT("/login")
    Call<Void> Login(@Body LoginDetails loginDetails);

    @PUT("/client/send/parking-status")
    Call<Void> updateParkingStatus(@Header("authorization") String authorization, @Body DeviceConfiguration deviceConfiguration);

    @PUT("/client/send/parked-cordinates")
    Call<Void> updateParkedCoordinates(@Header("authorization") String authorization, @Body Device device);

    @PUT("/client/send/timeout")
    Call<Void> updateTimeOut(@Header("authorization") String authorization, @Body DeviceConfiguration deviceConfiguration);

    @PUT("/client/send/radius")
    Call<Void> updateRadius(@Header("authorization") String authorization, @Body DeviceConfiguration deviceConfiguration);

    @PUT("/client/send/stolen-status")
    Call<Void> updateStolenStatus(@Header("authorization") String authorization, @Body DeviceConfiguration deviceConfiguration);

    @PUT("/client/send/change-password")
    Call<Void> updatePassword(@Header("email") String oldPassword, @Header("newPassword") String newPassword, @Header("authorization") String authorization);

    @POST("/client/send/create-new-user")
    Call<Void> createUserAccount(@Body User user);

    @POST("/client/send/create-new-device")
    Call<Void> createDevice(@Header("authorization") String authorization, @Body Device device);

    @GET("/client/receive/user-account")
    Call<User> getUserAccount(@Header("authorization") String authorization, @Header("email") String email);

    @GET("/client/receive/user-account-only-email")
    Call<User> getUserAccountOnlyEmail(@Header("email") String email);

    @GET("/client/{deviceId}/receive/device")
    Call<Device> getDevice(@Header("authorization") String authorization, @Path("deviceId") String deviceId);

    @GET("/client/{deviceId}/receive/device-only-deviceid")
    Call<Device> getDeviceOnlyDeviceId(@Path("deviceId") String deviceId);

    @GET("/client/{deviceId}/receive/device-pin")
    Call<DevicePin> getDevicePin(@Path("deviceId") String deviceId);

    @GET("/client/{deviceId}/receive/gps-coordinates")
    Call<GPSCoordinates> getGPSCoordinates(@Header("authorization") String authorization, @Path("deviceId") String deviceId);

    @GET("/client/{deviceId}/receive/gps-coordinates-for-day")
    Call<List<GPSCoordinates>> getGPSCoordinatesForDay(@Header("authorization") String authorization, @Path("deviceId") String deviceId, @Query("day") String day);

    @GET("/client/{deviceId}/receive/device-configuration")
    Call<DeviceConfiguration> getDeviceConfiguration(@Header("authorization") String authorization, @Path("deviceId") String deviceId);

    //String API_HOST = "http://10.0.2.2";  //localhost connection
    //String API_HOST = "http://api2.tues.dreamix.eu"; //Dreamix server
    String API_HOST = "http://130.204.140.70"; //home pc
    String API_PORT = "8080";

    class RetrofitInstance {
        private static Api service;
        public static Api create(Context context) {
            int CACHE_SIZE_BYTES = 1024 * 1024 * 2;
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            builder.cache(new Cache(context.getCacheDir(), CACHE_SIZE_BYTES));
            OkHttpClient client = builder.build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_HOST + ":" + API_PORT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            service = retrofit.create(Api.class);
            return service;
        }
    }
}