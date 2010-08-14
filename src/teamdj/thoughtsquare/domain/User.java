package teamdj.thoughtsquare.domain;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private int id;
    private String email;
    private String displayName;

    public User(int id, String email, String displayName) {
        this.id = id;
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
}
