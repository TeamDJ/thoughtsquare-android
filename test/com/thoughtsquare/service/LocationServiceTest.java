package com.thoughtsquare.service;

import com.thoughtsquare.domain.Location;
import com.thoughtsquare.utility.AHTTPClient;
import com.thoughtsquare.utility.AHTTPResponse;
import com.thoughtsquare.utility.Config;
import com.thoughtsquare.utility.JSONObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.valueOf;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class LocationServiceTest {
    private static final String BASE_URL = "base url";
    private static final int ID = 1;
    private static final String TITLE = "Brisbane";
    private static final double LATITUDE = -27.467581;
    private static final double LONGITUDE = 153.027893;

    @Test
    public void shouldAddLocationUsingHttpClient() {
        AHTTPResponse response = mock(AHTTPResponse.class);
        AHTTPClient httpClient = mock(AHTTPClient.class);
        Config config = mock(Config.class);
        LocationService service = new LocationService(config, httpClient);
        JSONObject jsonLocation = mock(JSONObject.class);

        when(config.getServerBaseURL()).thenReturn(BASE_URL);
        when(jsonLocation.getInt("id")).thenReturn(ID);
        when(response.getResponseStatus()).thenReturn(SC_CREATED);
        when(response.getJSONResponse()).thenReturn(jsonLocation);
        when(httpClient.post(anyString(), anyMap())).thenReturn(response);

        Location location = service.addLocation(TITLE, LATITUDE, LONGITUDE);

        assertThat(location.getId(), is(ID));
        assertThat(location.getTitle(), is(TITLE));
        assertThat(location.getLatitude(), is(LATITUDE));
        assertThat(location.getLongitude(), is(LONGITUDE));

        verify(httpClient).post(BASE_URL + "/locations.json", verifyAddLocationParams());
    }

    private Map<String, String> verifyAddLocationParams() {
        Map<String, String> postParams = new HashMap<String, String>();
        postParams.put("location[title]", TITLE);
        postParams.put("location[latitude]", valueOf(LATITUDE));
        postParams.put("location[longitude]", valueOf(LONGITUDE));
        return postParams;
    }
}
