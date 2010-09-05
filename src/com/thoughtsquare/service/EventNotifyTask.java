package com.thoughtsquare.service;

import com.thoughtsquare.domain.LocationEvent;

import java.util.Date;
import java.util.List;

public class EventNotifyTask implements Runnable {
    private NotificationService notificationService;
    private EventService eventService;
    private long delay;

    public EventNotifyTask(NotificationService notificationService, EventService eventService, long delay) {
        this.notificationService = notificationService;
        this.eventService = eventService;
        this.delay = delay;
    }

    public void run() {
        Date cutoff = new Date(System.currentTimeMillis() - delay);
        List<LocationEvent> events = eventService.getEventsSince(cutoff);

        for (LocationEvent event : events) {
            notificationService.sendNotification(event);
        }

    }
}
