package com.pm.pmproject.service;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.pm.pmproject.R;
import com.pm.pmproject.view.activity.MainActivity;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Calendar;

public class NotificationService extends Service{

    private TimerTask timerTask;
    private Timer timer;

    @Override
    public void onCreate() {
        Log.d("NotificationService", "onCreate");
        super.onCreate();
        timer = new Timer();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("NotificationService", "service started");

        super.onStartCommand(intent, flags, startId);

        if(timerTask != null)
            timerTask.cancel();

        // Schedule timer task (toast displaying) every 20 seconds
        timerTask = new TimerTask() {
            @Override
            public void run() {

                Log.d("NotificationService", "task run");

                NotificationManager notificationManager = getSystemService(NotificationManager.class);

                Intent ii = new Intent(NotificationService.this.getApplicationContext(), MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(NotificationService.this, 0, ii, 0);
                // Create the NotificationChannel, but only on API 26+ because
                // the NotificationChannel class is new and not in the support library
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                    CharSequence name = "pmproject_channel";
                    String description = "Channel for pmproject app";
                    int importance = NotificationManager.IMPORTANCE_DEFAULT;
                    NotificationChannel channel = new NotificationChannel("notify_001", name, importance);
                    channel.setDescription(description);
                    // Register the channel with the system; you can't change the importance
                    // or other notification behaviors after this

                    notificationManager.createNotificationChannel(channel);
                }

                NotificationCompat.Builder notificationBuilder =
                        new NotificationCompat.Builder(NotificationService.this.getApplicationContext(), "notify_001")
                                .setSmallIcon(R.mipmap.notification_icon)
                                .setContentTitle("Pm project")
                                .setContentText(getResources().getString(R.string.notification_text))
                                .setContentIntent(pendingIntent)
                                .setChannelId("notify_001");

                notificationManager.notify(001, notificationBuilder.build());

                stopSelf();
            }
        };

        // get lastTrainingDate
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("pmproject", Activity.MODE_MULTI_PROCESS);
        long lastTrainingDateMilis = preferences.getLong("lastTrainingDate", new Date().getTime());
        Log.d("asas", String.valueOf(lastTrainingDateMilis));
        Date lastTrainingDate = new Date(lastTrainingDateMilis);
        // add 7 days to last training date
        Calendar cal = Calendar.getInstance();
        cal.setTime(lastTrainingDate);
        //cal.add(Calendar.DATE, 7);
        // for presentation purpose notification is triggered after 1 minute
        cal.add(Calendar.MINUTE, 1);
        // trigger date is 7 days from now
        Date triggerDate = cal.getTime();
        // schedule timer task to fire after triggerDate
        timer.schedule(timerTask, triggerDate);

        // Recreate service when it is killed
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
