package teamdj.thoughtsquare.service;

import org.junit.Test;
import teamdj.thoughtsquare.utility.AHTTPClient;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
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

    @Test
    public void shouldSendEmailAndDisplayToServer() {
        AHTTPClient client = mock(AHTTPClient.class);
        RegisterService service = new RegisterService(client);

        when(client.post(anyString(), anyMap())).thenReturn(201);

        assertTrue(service.register(MY_EMAIL, MY_DISPLAY));

        Map<String, String> postParams = new HashMap<String, String>();
        postParams.put("user[display_name]", MY_DISPLAY);
        postParams.put("user[email]", MY_EMAIL);

        verify(client).post("http://thoughtsquare.heroku.com/users/", postParams);
    }
}
