package com.thoughtsquare.domain;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import com.thoughtsquare.utility.AHTTPClient;
import com.thoughtsquare.utility.AHTTPResponse;
import com.thoughtsquare.utility.Config;
import com.thoughtsquare.utility.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.valueOf;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNPROCESSABLE_ENTITY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class UserTest {

    private static final String MY_EMAIL = "my email";
    private static final String MY_DISPLAY = "my display";
    private static final String REGISTER_URL = "http://thoughtsquare.heroku.com/users.json";
    private static final String EDIT_USER_URL = "http://thoughtsquare.heroku.com/users/5.json";
    private static final int NEW_ID = 5;

    private AHTTPClient client;
    private JSONObject jsonResponse;
    private AHTTPResponse response;
    private User user;
    private UserProvider userProvider;
    private static final int NEW_LOCATION_ID = 1;
    private Config config;
    private static final String MOBILE_NUMBER = "0421222333";

    @Before
    public void setup() {
        userProvider = mock(UserProvider.class);
        client = mock(AHTTPClient.class);
        config = mock(Config.class);

        response = mock(AHTTPResponse.class);
        jsonResponse = mock(JSONObject.class);

        when(config.getServerBaseURL()).thenReturn("http://thoughtsquare.heroku.com");
        when(client.post(anyString(), anyMap())).thenReturn(response);
        when(client.put(anyString(), anyMap())).thenReturn(response);
        when(response.getJSONResponse()).thenReturn(jsonResponse);

        user = new User(userProvider, client, config, null, MY_EMAIL, MY_DISPLAY, MOBILE_NUMBER, null);
    }

    @Test
    public void shouldRegisterSuccessfullyWhenUserDoesNotExistOnServer(){
        JSONObject jsonUser = mock(JSONObject.class);
        when(response.getResponseStatus()).thenReturn(201);
        when(jsonResponse.getJSONObject("user")).thenReturn(jsonUser);
        when(jsonUser.getInt("id")).thenReturn(NEW_ID);

        assertTrue(user.register());

        assertThat(user.getId(), equalTo(NEW_ID));
        verify(client).post(REGISTER_URL, verifyRegisterPostParams());
        verify(userProvider).saveUser(user);
    }

    @Test
    public void shouldFailToRegisterIfUserExistsOnServer() {
        when(response.getResponseStatus()).thenReturn(SC_UNPROCESSABLE_ENTITY);

        assertFalse(user.register());
        verify(userProvider, never()).saveUser(user);
    }

    @Test
    public void shouldUpdateLocationSuccessfully() {
        User user = new User(userProvider, client, config, 5, MY_EMAIL, MY_DISPLAY,MOBILE_NUMBER, null);

        Location newLocation = mock(Location.class);

        when(newLocation.getId()).thenReturn(NEW_LOCATION_ID);
        when(response.getResponseStatus()).thenReturn(SC_OK);

        assertThat(user.getCurrentLocation(), equalTo(null));

        assertTrue(user.updateLocation(newLocation));
        assertThat(user.getCurrentLocation(), equalTo(newLocation));

        verify(client).put(EDIT_USER_URL, verifyUpdateLocationParams());
        verify(userProvider).saveUser(user);
    }

    @Test
    public void shouldSaveLocationEvenIfHttpPutFails(){
        User user = new User(userProvider, client, config, 5, MY_EMAIL, MY_DISPLAY, MOBILE_NUMBER, null);

        Location newLocation = mock(Location.class);

        when(newLocation.getId()).thenReturn(NEW_LOCATION_ID);
        when(response.getResponseStatus()).thenReturn(500);

        user.updateLocation(newLocation);

        verify(userProvider).saveUser(user);
    }

    private Map<String, String> verifyRegisterPostParams() {
        Map<String, String> postParams = new HashMap<String, String>();
        postParams.put("user[display_name]", MY_DISPLAY);
        postParams.put("user[email]", MY_EMAIL);
        postParams.put("user[mobile_number]", MOBILE_NUMBER);
        return postParams;
    }

    private Map<String, String> verifyUpdateLocationParams() {
        Map<String, String> postParams = new HashMap<String, String>();
        postParams.put("user[display_name]", MY_DISPLAY);
        postParams.put("user[email]", MY_EMAIL);
        postParams.put("user[mobile_number]", MOBILE_NUMBER);
        postParams.put("user[current_location_id]", valueOf(NEW_LOCATION_ID));
        return postParams;
    }

}
