package me.pfrison.polytimeima4.android;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import me.pfrison.polytimeima4.R;

public class HideNotificationBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        pref.edit().putBoolean("enableNotification", false).apply();

        // cancel notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.cancelAll();

        Toast.makeText(context, context.getResources().getString(R.string.dont_show_notifications), Toast.LENGTH_SHORT).show();
    }
}
