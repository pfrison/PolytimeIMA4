package me.pfrison.polytimeima4.android;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.preference.PreferenceManager;

public class BootCompleteReceiver extends BroadcastReceiver {
    @SuppressLint("UnsafeProtectedBroadcastReceiver") // protected via manifest
    @Override
    public void onReceive(Context context, Intent intent) {
        if(!PreferenceManager.getDefaultSharedPreferences(context).getBoolean("enableNotification", false))
            return;

        Intent serviceIntent = new Intent(context, NotificationService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            context.startForegroundService(serviceIntent);
        else
            context.startService(serviceIntent);
    }
}
