package com.thoughtsquare.service;

import com.thoughtsquare.domain.LocationEvent;
import com.thoughtsquare.utility.*;

import java.util.ArrayList;
import java.util.List;

public class EventService {
    private AHTTPClient httpClient;
    private Config config;

    public EventService(AHTTPClient httpClient, Config config) {
        this.httpClient = httpClient;
        this.config = config;
    }

    public List<LocationEvent> getEvents(){
        //TODO do a get events only since last time
        AHTTPResponse response = httpClient.get(config.getServerBaseURL() + "/events.json");

        if(response.getResponseStatus() == 200){
            return buildEvents(response.getResponseBody());
        }

        return new ArrayList<LocationEvent>();
    }

    private List<LocationEvent> buildEvents(String eventsJson) {
        ArrayList<LocationEvent> events = new ArrayList<LocationEvent>();
        JSONArray array = new JSONArray(eventsJson);

        for(int i=0; i < array.length(); i++){
            JSONObject object = array.getJSONObject(i);
            LocationEvent event = new LocationEvent(object.getString("title"), object.getString("message"));
            events.add(event);
        }


        return events;
    }
}

