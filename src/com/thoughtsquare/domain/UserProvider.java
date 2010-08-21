package com.thoughtsquare.domain;

import android.content.SharedPreferences;
import com.thoughtsquare.utility.AHTTPClient;
import com.thoughtsquare.utility.Config;

public class UserProvider {
    private static final String USER_DISPLAY_NAME = "user.displayName";
    private static final String USER_EMAIL = "user.email";
    private static final String USER_ID = "user.id";

    private SharedPreferences preferences;
    private AHTTPClient client;
    private Config config;
    private static final String USER_LOCATION_ID = "user.location.id";
    private static final String USER_LOCATION_TITLE = "user.location.title";
    private static final String USER_LOCATION_LATITUDE = "user.location.latitude";
    private static final String USER_LOCATION_LONGITUDE = "user.location.longitude";
    private static final String USER_LOCATION_RADIUS = "user.location.radius";

    public UserProvider(SharedPreferences preferences, AHTTPClient client, Config config) {
        this.preferences = preferences;
        this.client = client;
        this.config = config;
    }

    public User createUser(String email, String display) {
        return new User(this, client, config, null, email, display, null);
    }


    public void saveUser(User user) {
        preferences.edit()
                .putString(USER_DISPLAY_NAME, user.getDisplayName())
                .putString(USER_EMAIL, user.getEmail())
                .putInt(USER_ID, user.getId())
                .commit();

        if (user.currentLocationIsKnown()){
             Location location = user.getCurrentLocation();
            //TODO problem - preferences don't allow storing double!
             preferences.edit()
                .putInt(USER_LOCATION_ID, location.getId())
                .putString(USER_LOCATION_TITLE, location.getTitle())
                .putString(USER_LOCATION_LATITUDE, String.valueOf(location.getLatitude()))
                .putString(USER_LOCATION_LONGITUDE, String.valueOf(location.getLongitude()))
                .putFloat(USER_LOCATION_RADIUS, location.getRadius())
                .commit();
        }
    }

    public User getUser() {
        int userId = preferences.getInt(USER_ID, -1);
        if (userId == -1) {
            return null;
        }

        Location currentLocation = new Location(
            preferences.getInt(USER_LOCATION_ID, -1),
            preferences.getString(USER_LOCATION_TITLE, ""),
            Double.parseDouble(preferences.getString(USER_LOCATION_LATITUDE, "0.0")),
            Double.parseDouble(preferences.getString(USER_LOCATION_LONGITUDE, "0.0")),
            preferences.getFloat(USER_LOCATION_RADIUS, 0)
        );

        return new User(this, client, config, userId,
                preferences.getString(USER_EMAIL, ""),
                preferences.getString(USER_DISPLAY_NAME, ""),
                currentLocation);
    }

    public boolean userExists() {
        return preferences.getInt(USER_ID, 0) != 0;
    }
}
