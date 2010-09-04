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
import com.thoughtsquare.domain.LocationEvent;

import java.util.List;

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

                EventService eventService = new EventService();
                List<LocationEvent> events = eventService.getEvents();

                for (LocationEvent event : events) {
                    sendNotification(event);
                }
                handler.postDelayed(this, 30000);
            }
        };

        handler.postDelayed(runnable, 1000);
    }

    private void sendNotification(LocationEvent event) {
        Notification notification = createNotification(event);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(count++, notification);

    }

    private Notification createNotification(LocationEvent event) {
        int icon = R.drawable.stat_notify_chat;
        long when = System.currentTimeMillis();

        Notification notification = new Notification(icon, event.getTitle(), when);

        Context context = getApplicationContext();
        Intent notificationIntent = new Intent(context, ThoughtSquareActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, event.getTitle(), event.getMessage(), contentIntent);

        return notification;
    }
}


