package teamdj.thoughtsquare.service;

import org.junit.Before;
import org.junit.Test;
import teamdj.thoughtsquare.utility.AHTTPClient;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by IntelliJ IDEA.
 * User: jottaway
 * Date: Aug 11, 2010
 * Time: 1:49:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class RegisterServiceTest {
    private static final String MY_EMAIL = "my email";
    private static final String MY_DISPLAY = "my display";
    private static final String REGISTER_URL = "http://thoughtsquare.heroku.com/users.json";
    
    private AHTTPClient client;
    private RegisterService service;

    @Before
    public void setup(){
        client = mock(AHTTPClient.class);
        service = new RegisterService(client);
    }

    @Test
    public void shouldSendEmailAndDisplayToServer() {

        when(client.post(anyString(), anyMap())).thenReturn(201);

        assertTrue(service.register(MY_EMAIL, MY_DISPLAY));

        Map<String, String> postParams = new HashMap<String, String>();
        postParams.put("user[display_name]", MY_DISPLAY);
        postParams.put("user[email]", MY_EMAIL);

        verify(client).post(REGISTER_URL, postParams);
    }

    @Test
    public void shouldReturnFalseWhenPostToCreateUserFailed() {
        when(client.post(anyString(), anyMap())).thenReturn(422);

         assertFalse(service.register(MY_EMAIL, MY_DISPLAY));

        verify(client).post(eq(REGISTER_URL), anyMap());

    }
}
