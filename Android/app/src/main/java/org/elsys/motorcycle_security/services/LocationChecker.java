package org.elsys.motorcycle_security.services;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.elsys.motorcycle_security.R;
import org.elsys.motorcycle_security.activities.CurrentLocation;
import org.elsys.motorcycle_security.http.Api;
import org.elsys.motorcycle_security.models.Device;
import org.elsys.motorcycle_security.models.DeviceConfiguration;
import org.elsys.motorcycle_security.models.Globals;
import org.elsys.motorcycle_security.models.GpsCordinates;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationChecker extends Service {
    private double parkedX;
    private double parkedY;
    private double currentX;
    private double currentY;

    public LocationChecker() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Location checker", "Service running");
        final Api api = Api.RetrofitInstance.create();
        api.getDevice(Globals.deviceInUse, Globals.authorization).enqueue(new Callback<Device>() {
            @Override
            public void onResponse(Call<Device> call, Response<Device> response) {
                if(response.isSuccessful()) {
                    Device device = response.body();
                    parkedX = device.getParkedX();
                    parkedY = device.getParkedY();
                    Log.d("Location checker", "Response");
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
                    if(parkedX != currentX && parkedY != currentY) {
                        Log.d("Location checker", "Notify");
                        api.updateStolenStatus(Globals.deviceInUse,true, Globals.authorization).enqueue(new Callback<DeviceConfiguration>() {
                            @Override
                            public void onResponse(Call<DeviceConfiguration> call, Response<DeviceConfiguration> response) {}
                            @Override
                            public void onFailure(Call<DeviceConfiguration> call, Throwable t) {}
                        });
                        api.updateTimeOut(Globals.deviceInUse,10000, Globals.authorization).enqueue(new Callback<DeviceConfiguration>() {
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
        return START_STICKY;
    }

    public void scheduleLocationCheckerAlarm() {
        Intent intent = new Intent(getApplicationContext(), LocationCheckerReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, LocationCheckerReceiver.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long firstMillis = System.currentTimeMillis();
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis, 5000, pIntent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);

        Log.d("Location checker", "TASK REMOVED");
        scheduleLocationCheckerAlarm();
    }
    private Thread.UncaughtExceptionHandler defaultUEH;
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            Log.d("Location checker", "Uncaught exception start!");
            scheduleLocationCheckerAlarm();
            System.exit(2);
        }
    };

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
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Location checker", "Service destroyed");
    }
}
