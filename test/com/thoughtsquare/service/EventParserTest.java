package com.thoughtsquare.service;

import com.thoughtsquare.domain.LocationEvent;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class EventParserTest {
    private EventParser eventParser;

    @Before
    public void setup(){
        eventParser = new EventParser();
    }

    @Test
    public void shouldParseMultipleEvents(){

        String feed = "[{\"event\":{\"description\":\"james has arrived in hell\"," +
                "\"title\":\"new smurf on your turf\",\"user_id\":298486374," +
                "\"location_id\":113629430,\"when\":\"2010-09-05T08:12:12Z\",\"event_type\":\"arrive\"}}" +
                ",{\"event\":{\"description\":\"james has left hell\"," +
                "\"title\":\"smurf leaves\",\"user_id\":298486374," +
                "\"location_id\":113629430,\"when\":\"2010-09-05T08:12:12Z\",\"event_type\":\"depart\"}}" +
                "]";


        List<LocationEvent> events = eventParser.parseEvents(feed);

        assertThat(events.size(), is(2));
        assertThat(events.get(0).getTitle(), is("new smurf on your turf"));
        assertThat(events.get(0).getMessage(), is("james has arrived in hell"));
        assertThat(events.get(1).getTitle(), is("smurf leaves"));
        assertThat(events.get(1).getMessage(), is("james has left hell"));
    }

    @Test
    public void shouldReturnEmptyListIfServerReturnsNoEvents(){
        List<LocationEvent> events = eventParser.parseEvents("[]");
        assertThat(events.size(), is(0));
    }
}
