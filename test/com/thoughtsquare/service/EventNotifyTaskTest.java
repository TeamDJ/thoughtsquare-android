package com.thoughtsquare.service;

import com.thoughtsquare.domain.LocationEvent;
import org.joda.time.DateTimeUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Date;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class EventNotifyTaskTest {
    private EventNotifyTask task;
    private EventService eventService;
    private NotificationService notificationService;
    private long delay = 60000;

    @Before
    public void setup() {
        notificationService = mock(NotificationService.class);
        eventService = mock(EventService.class);
        task = new EventNotifyTask(notificationService, eventService, delay);

    }

    @Test
    public void shouldCreateANotificationForEachLocationEvent() {
        LocationEvent event1 = new LocationEvent("hello rudy", "a message for you");
        LocationEvent event2 = new LocationEvent("hello rudy", "another message for you");
        when(eventService.getEventsSince(any(Date.class))).thenReturn(asList(event1, event2));

        task.run();

        ArgumentCaptor<Date> argument = ArgumentCaptor.forClass(Date.class);
        verify(eventService).getEventsSince(argument.capture());
        assertTrue(System.currentTimeMillis() - argument.getValue().getTime() > delay);

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
