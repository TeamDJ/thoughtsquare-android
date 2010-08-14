package teamdj.thoughtsquare.builder;

import org.json.JSONException;
import org.json.JSONObject;
import teamdj.thoughtsquare.domain.User;

public class UserBuilder {
    public User fromJSON(String input) {
        try {
            JSONObject jsonUser = new JSONObject(input).getJSONObject("user");
            int id = jsonUser.getInt("id");
            String email = jsonUser.getString("email");
            String displayName = jsonUser.getString("display_name");
            return new User(id, email, displayName);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
