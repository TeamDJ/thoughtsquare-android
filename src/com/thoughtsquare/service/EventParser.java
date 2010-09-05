package com.thoughtsquare.service;

import com.thoughtsquare.domain.LocationEvent;
import com.thoughtsquare.utility.JSONArray;
import com.thoughtsquare.utility.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventParser {

    public List<LocationEvent> parseEvents(String eventsJson) {
        ArrayList<LocationEvent> events = new ArrayList<LocationEvent>();
        JSONArray array = new JSONArray(eventsJson);

        for(int i=0; i < array.length(); i++){
            JSONObject jsonEvent = array.getJSONObject(i).getJSONObject("event");
            LocationEvent event = new LocationEvent(jsonEvent.getString("title"), jsonEvent.getString("description"));
            events.add(event);
        }

        return events;
    }
}
