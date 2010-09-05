package com.thoughtsquare.service;

import com.thoughtsquare.domain.LocationEvent;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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


        List<LocationEvent> events = eventParser.parseEvents(feed, new Date(0));

        assertThat(events.size(), is(2));
        assertThat(events.get(0).getTitle(), is("new smurf on your turf"));
        assertThat(events.get(0).getMessage(), is("james has arrived in hell"));
        assertThat(events.get(1).getTitle(), is("smurf leaves"));
        assertThat(events.get(1).getMessage(), is("james has left hell"));
    }

    @Test
    public void shouldNotIncludeEventsWhichAreTooOld(){
        DateTimeFormatter format = ISODateTimeFormat.dateTimeNoMillis();
        DateTime cutoff = new DateTime().toDateTime(DateTimeZone.UTC);
        DateTime beforeCutoff = cutoff.minusDays(1);
        DateTime afterCutoff = cutoff.plusDays(1);


        String feed = "[{\"event\":{\"description\":\"james has arrived in hell\"," +
                "\"title\":\"new smurf on your turf\",\"user_id\":298486374," +
                "\"location_id\":113629430,\"when\":\""+ format.print(beforeCutoff) + "\",\"event_type\":\"arrive\"}}" +
                ",{\"event\":{\"description\":\"james has left hell\"," +
                "\"title\":\"smurf leaves\",\"user_id\":298486374," +
                "\"location_id\":113629430,\"when\":\""+ format.print(afterCutoff) + "\",\"event_type\":\"depart\"}}" +
                "]";

        List<LocationEvent> events = eventParser.parseEvents(feed, cutoff.toDate());

        assertThat(events.size(), is(1));
        assertThat(events.get(0).getTitle(), is("smurf leaves"));

    }

    @Test
    public void shouldReturnEmptyListIfServerReturnsNoEvents(){
        List<LocationEvent> events = eventParser.parseEvents("[]", new Date());
        assertThat(events.size(), is(0));
    }
}
