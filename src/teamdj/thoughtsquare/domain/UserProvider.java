package teamdj.thoughtsquare.domain;

import teamdj.thoughtsquare.utility.AHTTPClient;
import teamdj.thoughtsquare.utility.Config;

public class UserProvider {

    private AHTTPClient client;
    private Config config;

    public UserProvider(AHTTPClient client, Config config) {
        this.client = client;
        this.config = config;
    }

    public User createUser(String email, String display){
        return new User(this, client, config, email, display);
    }

    public void saveUser(User user){
        
    }
}
