package com.thoughtsquare.domain;

import org.apache.http.HttpStatus;
import com.thoughtsquare.utility.AHTTPClient;
import com.thoughtsquare.utility.AHTTPResponse;
import com.thoughtsquare.utility.Config;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.valueOf;
import static org.apache.http.HttpStatus.SC_OK;

public class User {
    private Integer id;
    private UserProvider userProvider;
    private AHTTPClient client;
    private Config config;
    private String email;
    private String displayName;

    private String mobileNumber;

    private Location currentLocation;

    public User(UserProvider userProvider, AHTTPClient client, Config config,
                Integer id, String email, String displayName, String mobileNumber, Location currentLocation) {
        this.userProvider = userProvider;
        this.client = client;
        this.config = config;
        this.id = id;
        this.email = email;
        this.displayName = displayName;
        this.mobileNumber = mobileNumber;
        this.currentLocation = currentLocation;
    }


    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public boolean register() {
        AHTTPResponse status = client.post(config.getServerBaseURL() + "/users.json", createParams());

        if (status.getResponseStatus() == HttpStatus.SC_CREATED) {
            id = status.getJSONResponse().getJSONObject("user").getInt("id");
            userProvider.saveUser(this);
            return true;
        }

        return false;
    }

    public boolean updateLocation(Location newLocation) {
        this.currentLocation = newLocation;

        AHTTPResponse status = client.put(config.getServerBaseURL() + "/users/" + id + ".json", createParams());

        userProvider.saveUser(this);
        if (status.getResponseStatus() == SC_OK) {
            return true;
        }

        return false;
    }

    private Map<String, String> createParams() {
        Map<String, String> putParams = new HashMap<String, String>();
        putParams.put("user[display_name]", displayName);
        putParams.put("user[email]", email);
        putParams.put("user[mobile_number]", mobileNumber);

        if(currentLocationIsKnown()){
            putParams.put("user[current_location_id]", valueOf(currentLocation.getId()));
        }

        return putParams;
    }


    public boolean currentLocationIsKnown() {
        return currentLocation != null;
    }


}
