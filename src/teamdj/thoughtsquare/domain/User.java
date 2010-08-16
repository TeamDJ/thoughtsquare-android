package teamdj.thoughtsquare.domain;

import org.apache.http.HttpStatus;
import teamdj.thoughtsquare.utility.AHTTPClient;
import teamdj.thoughtsquare.utility.AHTTPResponse;
import teamdj.thoughtsquare.utility.Config;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.valueOf;
import static org.apache.http.HttpStatus.SC_OK;

public class User {
    private int id;
    private UserProvider userProvider;
    private AHTTPClient client;
    private Config config;
    private String email;
    private String displayName;
    private Location currentLocation;

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

    public Location getCurrentLocation() {
        return currentLocation;
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

        return false;

    }

    public boolean updateLocation(Location newLocation) {
        Map<String, String> putParams = new HashMap<String, String>();
        putParams.put("user[display_name]", displayName);
        putParams.put("user[email]", email);
        putParams.put("user[current_location_id]", valueOf(newLocation.getId()));

        AHTTPResponse status = client.put(config.getServerBaseURL() + "/users/" + id + ".json", putParams);

        if (status.getResponseStatus() == SC_OK) {
            this.currentLocation = newLocation;
            return true;
        }

        return false;
    }
}
