package com.thoughtsquare.service;

import com.thoughtsquare.domain.LocationEvent;
import com.thoughtsquare.utility.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventService {
    private EventParser eventParser;
    private AHTTPClient httpClient;
    private Config config;

    public EventService(EventParser eventParser, AHTTPClient httpClient, Config config) {
        this.eventParser = eventParser;
        this.httpClient = httpClient;
        this.config = config;
    }

    public List<LocationEvent> getEvents(Date since){
        //TODO do a get events only since last time
        AHTTPResponse response = httpClient.get(config.getServerBaseURL() + "/events.json");

        if(response.getResponseStatus() == 200){
            return eventParser.parseEvents(response.getResponseBody(), since);
        }

        return new ArrayList<LocationEvent>();
    }


}

