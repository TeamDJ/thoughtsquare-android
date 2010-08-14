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

    public static User fromJSON(String input) throws JSONException {
        JSONObject jsonUser = new JSONObject(input).getJSONObject("user");
        int id = jsonUser.getInt("id");
        String email = jsonUser.getString("email");
        String displayName = jsonUser.getString("display_name");
        return new User(id, email, displayName);
    }
}
