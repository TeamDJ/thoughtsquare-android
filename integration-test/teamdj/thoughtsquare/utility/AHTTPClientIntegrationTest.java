package teamdj.thoughtsquare.utility;

import org.apache.http.HttpStatus;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.isNull;

/**
 * Created by IntelliJ IDEA.
 * User: jottaway
 * Date: Aug 11, 2010
 * Time: 2:45:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class AHTTPClientIntegrationTest {
    @Test
    public void testPost() throws Exception {
        AHTTPClient ahttpClient = new AHTTPClient();

        Map<String, String> postParams = new HashMap<String, String>();
        postParams.put("user[display_name]", "foo");
        postParams.put("user[email]", "foo@bar.com");

        AHTTPResponse response = ahttpClient.post("http://thoughtsquare.heroku.com/users.json", postParams);

        assertThat(response.getResponseStatus(), is(HttpStatus.SC_CREATED));
        assertThat(response.getResponseBody(), not(isNull()));
    }

    //TODO: Clean up created user after test.
}
