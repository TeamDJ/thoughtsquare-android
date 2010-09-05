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
import com.thoughtsquare.utility.AHTTPClient;
import com.thoughtsquare.utility.Config;
import com.thoughtsquare.utility.ConfigLoader;
import com.thoughtsquare.utility.RepeatableTask;

import java.util.List;

public class NotificationService extends Service {

    private Handler handler;
    private static final int LOCATION_EVENT = 1;

    public NotificationService(){
        this.handler = new Handler();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Config config = new ConfigLoader().getConfig(getApplicationContext());
        EventService eventService = new EventService(new AHTTPClient(), config);
        RepeatableTask task = new RepeatableTask(handler, new EventNotifyTask(this, eventService), 30000);
        //handler.postDelayed(task, 1000);
    }

    public void sendNotification(LocationEvent event) {
        Notification notification = createNotification(event);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(LOCATION_EVENT, notification);

    }

    private Notification createNotification(LocationEvent event) {
        int icon = R.drawable.stat_notify_chat;
        long when = System.currentTimeMillis();

        Notification notification = new Notification(icon, event.getTitle(), when);

        Intent notificationIntent = new Intent(this, ThoughtSquareActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification.setLatestEventInfo(this, event.getTitle(), event.getMessage(), contentIntent);

        return notification;
    }

    public void setHandler(Handler handler) {
        //grr setter-injection! needed to be able to test this class at all
        this.handler = handler;
    }
}


