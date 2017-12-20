package org.elsys.http;

import org.elsys.models.GpsCordinates;
import org.elsys.models.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;


public interface Api {

    @GET("/client/receive/user-account")
    Call<User> getUserAccount(@Header("userName") String userName);

    @GET("/client/receive/{id}/gps-cordinates")
    Call<GpsCordinates> getGpsCordinates(@Path("id") long id);
}