package teamdj.thoughtsquare.utility;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.isNull;

/**
 * Created by IntelliJ IDEA.
 * User: jottaway
 * Date: Aug 11, 2010
 * Time: 2:45:20 PM
 * To change this template use File | Settings | File Templates.
 */
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

        assertThat(response.getResponseStatus(), is(HttpStatus.SC_CREATED));
        assertThat(response.getResponseBody(), not(isNull()));
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
