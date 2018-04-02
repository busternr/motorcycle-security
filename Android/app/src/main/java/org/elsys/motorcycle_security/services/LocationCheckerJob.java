package org.elsys.motorcycle_security.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.util.Range;
import android.widget.Toast;

import org.elsys.motorcycle_security.R;
import org.elsys.motorcycle_security.activities.Main;
import org.elsys.motorcycle_security.http.Api;
import org.elsys.motorcycle_security.models.Device;
import org.elsys.motorcycle_security.models.DeviceConfiguration;
import org.elsys.motorcycle_security.models.GPSCoordinates;
import org.elsys.motorcycle_security.models.Globals;

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
        final Api api = Api.RetrofitInstance.create();
        api.getDevice(authorization, deviceId).enqueue(new Callback<Device>() {
            @Override
            public void onResponse(Call<Device> call, Response<Device> response) {
                if(response.isSuccessful()) {
                    Device device = response.body();
                    parkedX = device.getParkedX();
                    parkedY = device.getParkedY();
                    api.getGPSCoordinates(authorization, deviceId).enqueue(new Callback<GPSCoordinates>() {
                        @Override
                        public void onResponse(Call<GPSCoordinates> call, Response<GPSCoordinates> response) {
                            if (response.isSuccessful()) {
                                GPSCoordinates gpsCoordinates = response.body();
                                boolean moving = false;
                                currentX = gpsCoordinates.getX();
                                currentY = gpsCoordinates.getY();
                                if(Globals.radius == 0) {
                                    double minX = currentX - 0.05;
                                    double maxX = currentX + 0.05;
                                    Range<Double> rangeX = new Range<>(minX, maxX);
                                    double minY = currentY - 0.05;
                                    double maxY = currentY + 0.05;
                                    Range<Double> rangeY = new Range<>(minY, maxY);
                                    if (!rangeX.contains(parkedX) || !rangeY.contains(parkedY)) moving = true;
                                }
                                else if(Globals.radius > 0) {
                                    float[] distance = new float[2];
                                    Location.distanceBetween(currentX,currentY, Globals.circle.getCenter().latitude, Globals.circle.getCenter().longitude, distance);
                                    if(distance[0] > Globals.circle.getRadius()) moving = true;
                                }
                                if(parkedX == 0 || parkedY == 0) moving = false; //Against failed server response
                                if(moving) {
                                    DeviceConfiguration deviceConfiguration = new DeviceConfiguration();
                                    deviceConfiguration.setDeviceId(deviceId);
                                    deviceConfiguration.setStolen(true);
                                    api.updateStolenStatus(authorization, deviceConfiguration).enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {}
                                        @Override
                                        public void onFailure(Call<Void> call, Throwable t) {}
                                    });
                                    DeviceConfiguration deviceConfiguration2 = new DeviceConfiguration();
                                    deviceConfiguration2.setDeviceId(deviceId);
                                    deviceConfiguration2.setTimeOut(10000);
                                    api.updateTimeOut(authorization, deviceConfiguration2).enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {}
                                        @Override
                                        public void onFailure(Call<Void> call, Throwable t) {}
                                    });
                                    sendNotification();
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<GPSCoordinates> call, Throwable t) {
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<Device> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Server is not responding, please try again later.", Toast.LENGTH_LONG).show();
            }
        });
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.N) jobFinished(jobParameters, true);
        else jobFinished(jobParameters, false);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    protected void sendNotification() {
        Intent intent = new Intent(this, Main.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Notification notification = new Notification();
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationBuilder
                .setDefaults(notification.defaults)
                .setSmallIcon(R.drawable.icon)
                .setContentText("Tap to see current location.")
                .setContentIntent(pendingIntent);
        if(Globals.radius == 0) notificationBuilder.setContentTitle("Device " + getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("Current device in use", "") + " is moving!");
        else notificationBuilder.setContentTitle("Device " + getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("Current device in use", "") + " has just exited the range area!");
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 , notificationBuilder.build());
    }
}