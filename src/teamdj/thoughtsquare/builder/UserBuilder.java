package teamdj.thoughtsquare.builder;

import teamdj.thoughtsquare.domain.User;
import teamdj.thoughtsquare.utility.JSONObject;

public class UserBuilder {
    public User fromJSON(String input) {
            JSONObject jsonUser = new JSONObject(input).getJSONObject("user");
            int id = jsonUser.getInt("id");
            String email = jsonUser.getString("email");
            String displayName = jsonUser.getString("display_name");
            return new User(id, email, displayName);
    }
}
