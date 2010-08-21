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
             preferences.edit()
                .putInt(USER_LOCATION_ID, location.getId())
                .putString(USER_LOCATION_TITLE, location.getTitle())
                .putFloat(USER_LOCATION_LATITUDE, location.getLatitude())
                .putFloat(USER_LOCATION_LONGITUDE, location.getLongitude())
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
            preferences.getFloat(USER_LOCATION_LATITUDE, 0.0F),
            preferences.getFloat(USER_LOCATION_LONGITUDE, 0.0F)
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
