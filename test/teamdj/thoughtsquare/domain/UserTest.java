package teamdj.thoughtsquare.domain;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import teamdj.thoughtsquare.utility.AHTTPClient;
import teamdj.thoughtsquare.utility.AHTTPResponse;
import teamdj.thoughtsquare.utility.Config;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class UserTest {

    private static final String MY_EMAIL = "my email";
    private static final String MY_DISPLAY = "my display";
    private JSONObject jsonResponse;
    private static final String REGISTER_URL = "http://thoughtsquare.heroku.com/users.json";

    private AHTTPClient client;
    private AHTTPResponse response;
    private User user;
    private static final int NEW_ID = 5;

    @Before
    public void setup() {
        client = mock(AHTTPClient.class);
        Config config = mock(Config.class);
        response = mock(AHTTPResponse.class);
        jsonResponse = mock(JSONObject.class);

        when(config.getServerBaseURL()).thenReturn("http://thoughtsquare.heroku.com");
        when(client.post(anyString(), anyMap())).thenReturn(response);
        when(response.getJSONResponse()).thenReturn(jsonResponse);


        user = new User(client, config, MY_EMAIL, MY_DISPLAY);
    }

    @Test
    public void shouldRegisterSuccessfullyWhenUserDoesNotExistOnServer() throws JSONException {
        when(response.getResponseStatus()).thenReturn(201);
        JSONObject jsonUser = mock(JSONObject.class);
        when(jsonResponse.getJSONObject("user")).thenReturn(jsonUser);
        when(jsonUser.getInt("id")).thenReturn(NEW_ID);

        assertTrue(user.register());

        assertThat(user.getId(), equalTo(NEW_ID));
        verify(client).post(REGISTER_URL, verifyPostParams());
    }

    @Test
    public void shouldFailToRegisterIfUserExistsOnServer() {
        when(response.getResponseStatus()).thenReturn(422);

        assertFalse(user.register());
    }

    private Map<String, String> verifyPostParams() {
        Map<String, String> postParams = new HashMap<String, String>();
        postParams.put("user[display_name]", MY_DISPLAY);
        postParams.put("user[email]", MY_EMAIL);
        return postParams;
    }
}
