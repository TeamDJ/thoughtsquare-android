package teamdj.thoughtsquare.service;

import teamdj.thoughtsquare.utility.AHTTPClient;
import teamdj.thoughtsquare.utility.AHTTPResponse;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    private AHTTPClient client;

    public UserService(AHTTPClient client) {
        //To change body of created methods use File | Settings | File Templates.
        this.client = client;
    }

    public boolean register(String emailAddress, String displayName) {
        Map<String, String> postParams = new HashMap<String, String>();
        postParams.put("user[display_name]", displayName);
        postParams.put("user[email]", emailAddress);

        AHTTPResponse status = client.post("http://thoughtsquare.heroku.com/users.json", postParams);

        return status.getResponseStatus() == 201;
    }
}
