package teamdj.thoughtsquare.domain;

import org.hamcrest.Matchers;
import org.json.JSONException;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.eq;

public class UserTest {
    @Test
    public void shouldReturnUserFromValidJson() throws JSONException {
        String input = "{\"user\":{\"created_at\":\"2010-08-12T00:05:29Z\",\"updated_at\":\"2010-08-12T00:05:29Z\","
                + "\"id\":1,\"display_name\":\"Hello World\",\"email\":\"hello@world.com\"}}";

        User user = User.fromJSON(input);

        assertThat(user.getId(), equalTo(1));
        assertThat(user.getEmail(), equalTo("hello@world.com"));
        assertThat(user.getDisplayName(), equalTo("Hello World"));
    }
}
