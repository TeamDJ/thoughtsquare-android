package com.thoughtsquare.service;

import com.thoughtsquare.domain.LocationEvent;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

public class EventService {

    public List<LocationEvent> getEvents() {

        LocationEvent event = new LocationEvent("New smurf on your turf", "Julian has arrived in Brisbane");

        return asList(event);
    }
}
