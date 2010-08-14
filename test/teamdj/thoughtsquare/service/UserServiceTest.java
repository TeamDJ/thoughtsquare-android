package teamdj.thoughtsquare.service;

import org.junit.Before;
import org.junit.Test;
import teamdj.thoughtsquare.utility.AHTTPClient;
import teamdj.thoughtsquare.utility.AHTTPResponse;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    private static final String MY_EMAIL = "my email";
    private static final String MY_DISPLAY = "my display";
    private static final String REGISTER_URL = "http://thoughtsquare.heroku.com/users.json";

    private AHTTPClient client;
    private UserService service;

    @Before
    public void setup() {
        client = mock(AHTTPClient.class);

        service = new UserService(client);
    }

    @Test
    public void shouldSendEmailAndDisplayToServer() {
        AHTTPResponse response = mock(AHTTPResponse.class);
        when(response.getResponseStatus()).thenReturn(201);
        when(client.post(anyString(), anyMap())).thenReturn(response);

        assertTrue(service.register(MY_EMAIL, MY_DISPLAY));

        Map<String, String> postParams = new HashMap<String, String>();
        postParams.put("user[display_name]", MY_DISPLAY);
        postParams.put("user[email]", MY_EMAIL);

        verify(client).post(REGISTER_URL, postParams);
    }

    @Test
    public void shouldReturnFalseWhenPostToCreateUserFailed() {
        AHTTPResponse response = mock(AHTTPResponse.class);
        when(response.getResponseStatus()).thenReturn(422);
        when(client.post(anyString(), anyMap())).thenReturn(response);

        assertFalse(service.register(MY_EMAIL, MY_DISPLAY));

        verify(client).post(eq(REGISTER_URL), anyMap());
    }
}
