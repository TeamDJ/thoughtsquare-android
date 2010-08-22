package com.thoughtsquare.utility;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.isNull;


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

        AHTTPResponse response = ahttpClient.post("http://localhost:2010/users.json", postParams);

        assertThat(response.getResponseStatus(), is(HttpStatus.SC_CREATED));
        assertTrue(response.getResponseBody().contains("foo"));
    }
    
    @Test
    public void shouldBeAbleToDoSecondRequest(){
        Map<String, String> postParams = new HashMap<String, String>();
               postParams.put("user[display_name]", "foo");
               postParams.put("user[email]", "foo@bar.com");
        ahttpClient.post("http://localhost:2010/users.json", postParams);
        ahttpClient.post("http://localhost:2010/users.json", postParams);
    }

    @Test
    public void shouldPutSuccessfully() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("user[display_name]", "James");
        params.put("user[email]", "foo@bar.com");
        params.put("user[current_location_id]", "2");

        AHTTPResponse response = ahttpClient.put("http://thoughtsquare.heroku.com/users/1.json", params);

        assertThat(response.getResponseStatus(), is(HttpStatus.SC_OK));
    }

    //TODO: Clean up created user after test.
}
