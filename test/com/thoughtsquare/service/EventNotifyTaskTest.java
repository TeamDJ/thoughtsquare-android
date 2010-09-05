package com.thoughtsquare.service;

import com.thoughtsquare.domain.LocationEvent;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;

public class EventNotifyTaskTest {
    private EventNotifyTask task;
    private EventService eventService;
    private NotificationService notificationService;

    @Before
    public void setup() {
        notificationService = mock(NotificationService.class);
        eventService = mock(EventService.class);
        task = new EventNotifyTask(notificationService, eventService);

    }

    @Test
    public void shouldCreateANotificationForEachLocationEvent() {
        LocationEvent event1 = new LocationEvent("hello rudy", "a message for you");
        LocationEvent event2 = new LocationEvent("hello rudy", "another message for you");
        when(eventService.getEventsSince(any(Date.class))).thenReturn(asList(event1, event2));

        task.run();

        verify(notificationService).sendNotification(event1);
        verify(notificationService).sendNotification(event2);
    }

    @Test
    public void shouldNotBarfWhenThereAreNoEvents(){
       when(eventService.getEventsSince(any(Date.class))).thenReturn(new ArrayList<LocationEvent>());

        task.run();

        verifyZeroInteractions(notificationService);
    }
}
