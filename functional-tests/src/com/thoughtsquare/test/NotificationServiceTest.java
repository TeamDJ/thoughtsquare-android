package com.thoughtsquare.test;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.test.ServiceTestCase;
import android.test.mock.MockContext;
import com.thoughtsquare.domain.LocationEvent;
import com.thoughtsquare.service.EventNotifyTask;
import com.thoughtsquare.service.NotificationService;
import com.thoughtsquare.utility.RepeatableTask;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NotificationServiceTest extends ServiceTestCase<NotificationService>{
    private Handler handler;
    private NotificationManager notificationManager;

    public NotificationServiceTest(Class<NotificationService> serviceClass) {
        super(serviceClass);
    }

    @Override
    protected void setUp() {
        Context context = mock(Context.class);
        handler = mock(Handler.class);
        notificationManager = mock(NotificationManager.class);
        when(context.getSystemService(Context.NOTIFICATION_SERVICE)).thenReturn(notificationManager);
        setContext(context);
        getService().setHandler(handler);

    }

    public void testSchedulesEventNotifyTask(){
        bindService(new Intent());

        ArgumentCaptor<RepeatableTask> argument = ArgumentCaptor.forClass(RepeatableTask.class);
        verify(handler).postDelayed(argument.capture(), 1000);
        assertTrue(argument.getValue().getTask() instanceof EventNotifyTask);
    }

    public void testSendNotification(){
        bindService(new Intent());

        getService().sendNotification(new LocationEvent("arrival", "smurf arrives in brisbane"));
        ArgumentCaptor<Notification> argument = ArgumentCaptor.forClass(Notification.class);
        verify(notificationManager).notify(1, argument.capture());

        assertEquals("",argument.getValue().toString());
    }

}
