package teamdj.thoughtsquare.domain;

import android.content.SharedPreferences;
import teamdj.thoughtsquare.utility.AHTTPClient;
import teamdj.thoughtsquare.utility.Config;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static teamdj.thoughtsquare.Preferences.DISPLAY_NAME;
import static teamdj.thoughtsquare.Preferences.USERNAME;

public class UserProvider {

    private SharedPreferences preferences;
    private AHTTPClient client;
    private Config config;
    private static final String USER_DISPLAY_NAME = "user.displayName";
    private static final String USER_EMAIL = "user.email";
    private static final String USER_ID = "user.id";

    public UserProvider(SharedPreferences preferences, AHTTPClient client, Config config) {
        this.preferences = preferences;
        this.client = client;
        this.config = config;
    }

    public User createUser(String email, String display){
        return new User(this, client, config, email, display);
    }

    public void saveUser(User user){
        preferences.edit()
                .putString(USER_DISPLAY_NAME, user.getDisplayName())
                .putString(USER_EMAIL, user.getEmail())
                .putInt(USER_ID, user.getId())
                .commit();
    }

    public User getUser(){
        int userId = preferences.getInt(USER_ID, 0);
        if(userId == 0){
            return null;
        }

        String displayName = preferences.getString(USER_DISPLAY_NAME, "");
        String email = preferences.getString(USER_EMAIL, "");

        return createUser(email, displayName);
    }

    public boolean doesUserExist(){
        return  preferences.getInt(USER_ID, 0) != 0;
    }
}
