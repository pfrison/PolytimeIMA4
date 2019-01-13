package me.pfrison.polytimeima4.android;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import me.pfrison.polytimeima4.R;
import me.pfrison.polytimeima4.graphics.MainPanelManager;
import me.pfrison.polytimeima4.timetable.Lesson;
import me.pfrison.polytimeima4.utils.TimeTableUtil;

public class NotificationService extends Service {
    private static final long sleepDuration = 300000; // 300 000 ms = 5 min
    private static final String channelID = "Polytime IM4";
    public static final int notificationID = 2100;

    private PendingIntent notificationClickIntent;
    private PendingIntent notificationDontShowIntent;

    private SharedPreferences pref;
    private boolean active = true;
    private Context context;
    private NotificationManagerCompat notificationManager;
    private boolean notificationNotified;

    private Handler refreshHandler;
    private Runnable refreshNotification = new Runnable() {
        @Override
        public void run() {
            // user asked for notification ?
            if(pref.getBoolean("enableNotification", false)){
                Lesson nextLesson = TimeTableUtil.getNextLesson30Mins(MainPanelManager.timeTable);
                if(nextLesson == null) {
                    notificationManager.cancelAll();
                    notificationNotified = false;
                }else if(!notificationNotified){
                    boolean minimal = pref.getBoolean("priorityMinimal", true);
                    NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(context, channelID)
                            .setSmallIcon(R.drawable.ic_notification)
                            .setContentTitle(nextLesson.name)
                            .setContentText(nextLesson.room)
                            .setPriority(minimal ? NotificationCompat.PRIORITY_MIN : NotificationCompat.PRIORITY_LOW)
                            .addAction(R.drawable.ic_alarm_off_black_24dp,
                                    getResources().getString(R.string.dont_show_notifications_action),
                                    notificationDontShowIntent)
                            .setContentIntent(notificationClickIntent)
                            .setShowWhen(false)
                            .setAutoCancel(false);
                    notificationManager.notify(notificationID,  notifBuilder.build());
                    notificationNotified = true;
                }
            }else{
                stopService(new Intent(NotificationService.this, NotificationService.class));
                return;
            }

            // continue ? + sleep
            if(active)
                refreshHandler.postDelayed(this, sleepDuration);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // startForeground notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String CHANNEL_ID = "my_service";
            String CHANNEL_NAME = "My Background Service";

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_NONE);
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setCategory(Notification.CATEGORY_SERVICE).setPriority(NotificationCompat.PRIORITY_MIN).build();
            startForeground(1, notification);
        }

        // normal start
        Intent intentAction = new Intent(this, MainActivity.class);
        intentAction.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.notificationClickIntent = PendingIntent.getActivity(this, 0, intentAction, 0);

        intentAction = new Intent(this, HideNotificationBroadcastReceiver.class);
        intentAction.setAction(getResources().getString(R.string.dont_show_notifications_action));
        notificationDontShowIntent = PendingIntent.getBroadcast(this, 0, intentAction, 0);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        this.context = this;
        this.notificationManager = NotificationManagerCompat.from(context);
        this.notificationNotified = false;

        refreshHandler = new Handler();
        refreshHandler.postDelayed(refreshNotification, sleepDuration);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        active = false;
        super.onDestroy();
    }
}
