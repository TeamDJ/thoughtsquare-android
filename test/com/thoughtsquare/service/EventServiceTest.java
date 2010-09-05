package com.thoughtsquare.service;

import com.thoughtsquare.domain.LocationEvent;
import com.thoughtsquare.utility.AHTTPClient;
import com.thoughtsquare.utility.AHTTPResponse;
import com.thoughtsquare.utility.Config;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EventServiceTest {
    private static final String BASEURL = "baseurl";
    private EventService eventService;
    private AHTTPClient httpClient;
    private AHTTPResponse response;

    @Before
    public void setup(){
        httpClient = mock(AHTTPClient.class);
        Config config = mock(Config.class);
        eventService = new EventService(httpClient, config);

        when(config.getServerBaseURL()).thenReturn(BASEURL);
        response = mock(AHTTPResponse.class);
        when(httpClient.get(BASEURL + "/events.json")).thenReturn(response);
    }

    @Test
    public void shouldGetListOfEventsFromServer(){
        String feed = "[" +
                "{\"title\" = \"smurf arrives\", \"message\" = \"jbomb is on the scene\"}," +
                "{\"title\" = \"smurf leaves\", \"message\" = \"jbomb is no longer on the scene\"}" +
                "]";

        when(response.getResponseStatus()).thenReturn(200);
        when(response.getResponseBody()).thenReturn(feed);
        List<LocationEvent> events = eventService.getEvents();
        assertThat(events.size(), is(2));
        assertThat(events.get(0).getTitle(), is("smurf arrives"));
        assertThat(events.get(0).getMessage(), is("jbomb is on the scene"));
        assertThat(events.get(1).getTitle(), is("smurf leaves"));
        assertThat(events.get(1).getMessage(), is("jbomb is no longer on the scene"));

    }

    @Test
    public void shouldReturnEmptyListIfResponseIsNotOk(){


    }


    @Test
    public void shouldReturnEmptyListIfResponseBodyIsPoo(){
            

    }
}
