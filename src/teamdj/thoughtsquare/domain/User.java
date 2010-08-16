package teamdj.thoughtsquare.domain;

import org.apache.http.HttpStatus;
import teamdj.thoughtsquare.utility.AHTTPClient;
import teamdj.thoughtsquare.utility.AHTTPResponse;
import teamdj.thoughtsquare.utility.Config;

import java.util.HashMap;
import java.util.Map;

public class User {
    private int id;
    private UserProvider userProvider;
    private AHTTPClient client;
    private Config config;
    private String email;
    private String displayName;

    public User(UserProvider userProvider, AHTTPClient client, Config config, String email, String displayName) {
        this.userProvider = userProvider;
        this.client = client;
        this.config = config;
        this.email = email;
        this.displayName = displayName;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }


    public boolean register() {
        Map<String, String> postParams = new HashMap<String, String>();
        postParams.put("user[display_name]", displayName);
        postParams.put("user[email]", email);

        AHTTPResponse status = client.post(config.getServerBaseURL() + "/users.json", postParams);

        if (status.getResponseStatus() == HttpStatus.SC_CREATED) {
            id = status.getJSONResponse().getJSONObject("user").getInt("id");
            userProvider.saveUser(this);
            return true;
        }
        else{
            return false;
        }
    }

    public void updateLocation(){
        // call client to update location here.
    }
}
