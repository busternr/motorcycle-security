package org.elsys.motorcycle_security.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import org.elsys.motorcycle_security.R;
import org.elsys.motorcycle_security.activities.CurrentLocation;
import org.elsys.motorcycle_security.http.Api;
import org.elsys.motorcycle_security.models.Device;
import org.elsys.motorcycle_security.models.Globals;
import org.elsys.motorcycle_security.models.GpsCordinates;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationChecker extends IntentService {
    private long parkedX;
    private long parkedY;
    private long currentX;
    private long currentY;

    public LocationChecker() {
        super("LocationChecker");
    }

   /* public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Location checker", "Service running");
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Location checker", "Service destroyed");
    }
*/
    @Override
    protected void onHandleIntent(Intent intent) {
        // Do the task here
       // Log.d("Location checker", "Service running");
        Api api = Api.RetrofitInstance.create();
        api.getDevice(Globals.deviceInUse).enqueue(new Callback<Device>() {
            @Override
            public void onResponse(Call<Device> call, Response<Device> response) {
                if(response.isSuccessful()) {
                        Device device = response.body();
                        parkedX = device.getParkedX();
                        parkedY = device.getParkedY();
                    }
                }
            @Override
            public void onFailure(Call<Device> call, Throwable t) {
            }
        });
        api.getGPSCordinates(Globals.deviceInUse, Globals.authorization).enqueue(new Callback<GpsCordinates>() {
            @Override
            public void onResponse(Call<GpsCordinates> call, Response<GpsCordinates> response) {
                if (response.isSuccessful()) {
                    GpsCordinates gpsCordinates = response.body();
                    currentX = gpsCordinates.getX();
                    currentY = gpsCordinates.getY();
                    //Later to be changed !!!
                    if(parkedX != currentX && parkedY != currentY) {
                        sendNotification();
                    }
                }
            }
            @Override
            public void onFailure(Call<GpsCordinates> call, Throwable t) {
            }
        });
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
                .setContentTitle("Device " + Globals.deviceInUse + " is moving !")
                .setContentText("Tap to see current location")
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 , notificationBuilder.build());
    }
}