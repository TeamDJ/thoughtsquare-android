package teamdj.thoughtsquare.service;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import teamdj.thoughtsquare.builder.UserBuilder;
import teamdj.thoughtsquare.domain.User;
import teamdj.thoughtsquare.utility.AHTTPClient;
import teamdj.thoughtsquare.utility.AHTTPResponse;
import teamdj.thoughtsquare.utility.Config;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    private static final String MY_EMAIL = "my email";
    private static final String MY_DISPLAY = "my display";
    private static final String USER_JSON = "json representation of a user";
    private static final String REGISTER_URL = "http://thoughtsquare.heroku.com/users.json";

    private AHTTPClient client;
    private UserService service;
    private UserBuilder builder;
    private AHTTPResponse response;
    private User user;

    @Before
    public void setup() {
        client = mock(AHTTPClient.class);
        builder = mock(UserBuilder.class);
        Config config = mock(Config.class);
        response = mock(AHTTPResponse.class);
        user = mock(User.class);

        when(config.getServerBaseURL()).thenReturn("http://thoughtsquare.heroku.com");
        when(client.post(anyString(), anyMap())).thenReturn(response);

        service = new UserService(config,client, builder);
    }

    @Test
    public void shouldSendEmailAndDisplayToServerAndReturnUser() {
        when(response.getResponseStatus()).thenReturn(201);
        when(response.getResponseBody()).thenReturn(USER_JSON);
        when(builder.fromJSON(USER_JSON)).thenReturn(user);

        assertThat(service.register(MY_EMAIL, MY_DISPLAY), equalTo(user));

        verify(client).post(REGISTER_URL, verifyPostParams());
        verify(builder).fromJSON(USER_JSON);
    }

    @Test
    public void shouldReturnFalseWhenPostToCreateUserFailed() {
        when(response.getResponseStatus()).thenReturn(422);
        when(response.getResponseBody()).thenReturn(USER_JSON);

        assertThat(service.register(MY_EMAIL, MY_DISPLAY), equalTo(null));

        verify(client).post(REGISTER_URL, verifyPostParams());
        verify(builder, never()).fromJSON(USER_JSON);
    }

    private Map<String, String> verifyPostParams() {
        Map<String, String> postParams = new HashMap<String, String>();
        postParams.put("user[display_name]", MY_DISPLAY);
        postParams.put("user[email]", MY_EMAIL);
        return postParams;
    }
}
