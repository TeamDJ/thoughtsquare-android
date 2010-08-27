package com.thoughtsquare.service;

import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import com.thoughtsquare.activity.ThoughtSquareActivity;

public class NotificationService extends Service {

    private Handler handler = new Handler();
    private static int count = 1;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        Runnable runnable = new Runnable() {

        public void run() {

            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            int icon = R.drawable.stat_notify_chat;
            CharSequence tickerText = "Hello";
            long when = System.currentTimeMillis();

            Notification notification = new Notification(icon, tickerText, when);

            Context context = getApplicationContext();
            CharSequence contentTitle = "My notification";
            CharSequence contentText = "Hello World!";

            Intent notificationIntent = new Intent(context, ThoughtSquareActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
            notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

            mNotificationManager.notify(count++, notification);

            Log.e("foo", "foofoo");

            handler.postDelayed(this, 30000);
        }

        };

        handler.postDelayed(runnable, 1000);
    }
}


