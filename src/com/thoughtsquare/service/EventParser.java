package com.thoughtsquare.service;

import com.thoughtsquare.domain.LocationEvent;
import com.thoughtsquare.utility.JSONArray;
import com.thoughtsquare.utility.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventParser {

    public List<LocationEvent> parseEvents(String eventsJson, Date eventStartDate) {

        ArrayList<LocationEvent> events = new ArrayList<LocationEvent>();
        JSONArray array = new JSONArray(eventsJson);

        for(int i=0; i < array.length(); i++){
            JSONObject jsonEvent = array.getJSONObject(i).getJSONObject("event");
            Date eventDate = toDate(jsonEvent.getString("when"));

            if(eventDate.after(eventStartDate)){
                LocationEvent event = new LocationEvent(jsonEvent.getString("title"), jsonEvent.getString("description"));
                events.add(event);
            }
        }

        return events;
    }

    private Date toDate(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        String str = dateStr.replace("Z", "+0000");

        Date eventDate = null;
        try {
            eventDate = format.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return eventDate;
    }


}
