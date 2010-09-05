package com.thoughtsquare.service;

import com.thoughtsquare.utility.AHTTPClient;
import com.thoughtsquare.utility.AHTTPResponse;
import com.thoughtsquare.utility.Config;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class EventServiceTest {
    private static final String BASEURL = "baseurl";
    private EventService eventService;
    private AHTTPClient httpClient;
    private AHTTPResponse response;
    private EventParser eventParser;

    @Before
    public void setup(){
        httpClient = mock(AHTTPClient.class);
        Config config = mock(Config.class);
        eventParser = mock(EventParser.class);
        response = mock(AHTTPResponse.class);

        when(config.getServerBaseURL()).thenReturn(BASEURL);
        when(httpClient.get(anyString())).thenReturn(response);

        eventService = new EventService(eventParser, httpClient, config);
    }

    @Test
    public void shouldGetListOfEventsFromServer(){
        String json = "some json";
        when(response.getResponseStatus()).thenReturn(200);
        when(response.getResponseBody()).thenReturn(json);
        List events = mock(List.class);
        when(eventParser.parseEvents(anyString(), any(Date.class))).thenReturn(events);

        Date date = new Date();
        assertThat(eventService.getEventsSince(date), is(events));

        verify(eventParser).parseEvents(json, date);
        verify(httpClient).get(BASEURL + "/events.json");
    }

    @Test
    public void shouldReturnEmptyListIfResponseIsNotOk(){
        when(response.getResponseStatus()).thenReturn(500);
        
        assertThat(eventService.getEventsSince(new Date()).size(), is(0));
    }


    
}
