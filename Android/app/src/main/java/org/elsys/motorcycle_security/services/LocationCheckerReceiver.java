package org.elsys.motorcycle_security.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LocationCheckerReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, LocationNotificator.class);
        context.startService(i);
    }
}