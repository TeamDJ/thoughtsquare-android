package teamdj.thoughtsquare.service;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import teamdj.thoughtsquare.builder.UserBuilder;
import teamdj.thoughtsquare.domain.User;
import teamdj.thoughtsquare.utility.AHTTPClient;
import teamdj.thoughtsquare.utility.AHTTPResponse;
import teamdj.thoughtsquare.utility.Config;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    private Config config;
    private AHTTPClient client;
    private UserBuilder builder;

    public UserService(Config config, AHTTPClient client, UserBuilder builder) {
        this.config = config;
        this.client = client;
        this.builder = builder;
    }

    public User register(String emailAddress, String displayName) {
        Map<String, String> postParams = new HashMap<String, String>();
        postParams.put("user[display_name]", displayName);
        postParams.put("user[email]", emailAddress);

        AHTTPResponse status = client.post(config.getServerBaseURL() + "/users.json", postParams);

        User user = null;
        if (status.getResponseStatus() == HttpStatus.SC_CREATED) {
            user = builder.fromJSON(status.getResponseBody());
        }
        return user;
    }
}
