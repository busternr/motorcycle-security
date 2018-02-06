package org.elsys.motorcycle_security.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import org.elsys.motorcycle_security.R;
import org.elsys.motorcycle_security.activities.CurrentLocation;
import org.elsys.motorcycle_security.http.Api;
import org.elsys.motorcycle_security.models.Device;
import org.elsys.motorcycle_security.models.DeviceConfiguration;
import org.elsys.motorcycle_security.models.GpsCordinates;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LocationCheckerJob extends JobService {
    private double parkedX;
    private double parkedY;
    private double currentX;
    private double currentY;
    private String deviceId;
    private String authorization;

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        deviceId = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("Current device in use", "");
        authorization = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("Authorization", "");
        Log.d("Job","onStartJob");
        final Api api = Api.RetrofitInstance.create();
        api.getDevice(deviceId, authorization).enqueue(new Callback<Device>() {
            @Override
            public void onResponse(Call<Device> call, Response<Device> response) {
                if(response.isSuccessful()) {
                    Device device = response.body();
                    parkedX = device.getParkedX();
                    parkedY = device.getParkedY();
                    Log.d("Job", "Response from device request");
                }
            }
            @Override
            public void onFailure(Call<Device> call, Throwable t) {
            }
        });
        api.getGPSCordinates(deviceId, authorization).enqueue(new Callback<GpsCordinates>() {
            @Override
            public void onResponse(Call<GpsCordinates> call, Response<GpsCordinates> response) {
                if (response.isSuccessful()) {
                    GpsCordinates gpsCordinates = response.body();
                    currentX = gpsCordinates.getX();
                    currentY = gpsCordinates.getY();
                    DecimalFormat df = new DecimalFormat("#.###");
                    df.setRoundingMode(RoundingMode.CEILING);
                    String.valueOf(df.format(currentX));
                    String.valueOf(df.format(currentY));
                    Log.d("X", Double.toString(currentX));
                    Log.d("Y", Double.toString(currentY));
                    Log.d("YEA BOIIII", "AM CHECKING THIS SHIET");
                    //Rounding GPS coordinates, because GPS module is not very accurate
                    if(parkedX != currentX || parkedX != currentX + 0.002 || parkedX != currentX - 0.002 && parkedY != currentY || parkedY != currentY + 0.002 || parkedY != currentY - 000.2) {
                        Log.d("YEA BOIIII", "NIGGA STOLE YOUR BIKE");
                        Log.d("Job", "Notify");
                        api.updateStolenStatus(deviceId,true,authorization).enqueue(new Callback<DeviceConfiguration>() {
                            @Override
                            public void onResponse(Call<DeviceConfiguration> call, Response<DeviceConfiguration> response) {}
                            @Override
                            public void onFailure(Call<DeviceConfiguration> call, Throwable t) {}
                        });
                        api.updateTimeOut(deviceId,10000, authorization).enqueue(new Callback<DeviceConfiguration>() {
                            @Override
                            public void onResponse(Call<DeviceConfiguration> call, Response<DeviceConfiguration> response) {}
                            @Override
                            public void onFailure(Call<DeviceConfiguration> call, Throwable t) {}
                        });
                        sendNotification();
                    }
                }
            }
            @Override
            public void onFailure(Call<GpsCordinates> call, Throwable t) {
            }
        });

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.N){
            jobFinished(jobParameters, true);
        }else {
            jobFinished(jobParameters, false);
        }
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d("Job","onStopJob Called");
        return false;
    }

    protected void sendNotification() {
        Intent intent = new Intent(this, CurrentLocation.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Notification notification = new Notification();
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationBuilder
                .setDefaults(notification.defaults)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("Device " + getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("Current device in use", "") + " is moving !")
                .setContentText("Tap to see current location")
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 , notificationBuilder.build());
    }
}