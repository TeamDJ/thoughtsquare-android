package com.thoughtsquare.utility;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;


public class AHTTPClientIntegrationTest {
    private AHTTPClient ahttpClient;

    @Before
    public void setUp() {
        ahttpClient = new AHTTPClient();
    }

    @Test
    public void shouldPostSuccessfully() throws Exception {
        Map<String, String> postParams = new HashMap<String, String>();
        postParams.put("user[display_name]", "foo");
        postParams.put("user[email]", "foo@bar.com");

        AHTTPResponse response = ahttpClient.post("http://thoughtsquare.heroku.com/users.json", postParams);

        assertThat(response.getResponseStatus(), is(SC_CREATED));
        assertTrue(response.getResponseBody().contains("foo"));
    }

    @Test
    public void shouldBeAbleToDoSecondRequest() {
        Map<String, String> postParams = new HashMap<String, String>();
        postParams.put("user[display_name]", "foo");

        postParams.put("user[email]", "one@test.com");
        ahttpClient.post("http://thoughtsquare.heroku.com/users.json", postParams);

        postParams.put("user[email]", "two@test.com");
        ahttpClient.post("http://thoughtsquare.heroku.com/users.json", postParams);
    }

    @Test
    public void shouldAddLocationSuccessfully() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("location[title]", "Test Location");
        params.put("location[latitude]", "-12.34");
        params.put("location[longitude]", "666");

        AHTTPResponse response = ahttpClient.post("http://thoughtsquare.heroku.com/locations.json", params);

        assertThat(response.getResponseStatus(), is(SC_CREATED));
    }

    @Test
    public void shouldPutSuccessfully() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("user[display_name]", "James");
        params.put("user[email]", "foo@bar.com");
        params.put("user[current_location_id]", "1");

        AHTTPResponse response = ahttpClient.put("http://thoughtsquare.heroku.com/users/1.json", params);

        assertThat(response.getResponseStatus(), is(SC_OK));
    }

    @Test
    public void shouldGetLocations() {
        AHTTPResponse response = ahttpClient.get("http://thoughtsquare.heroku.com/locations.json");

        assertThat(response.getResponseStatus(), is (SC_OK));
    }

    @Test
    public void shouldGetSuccessfully(){
        AHTTPResponse response = ahttpClient.get("http://localhost:2010/events.json");
        assertThat(response.getResponseStatus(), is(200));
        assertThat(response.getResponseBody(), is("[{\"title\": \"new smurf on your turf\", \"message\":\"papa smurf arrived in brisbane\" }]"));
    }

    //TODO: Clean up created user after test.
}
