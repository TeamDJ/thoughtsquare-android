package teamdj.thoughtsquare.service;

import teamdj.thoughtsquare.utility.AHTTPClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jottaway
 * Date: Aug 11, 2010
 * Time: 1:45:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class RegisterService {
    private AHTTPClient client;

    public RegisterService(AHTTPClient client) {
        //To change body of created methods use File | Settings | File Templates.
        this.client = client;
    }


    public boolean register(String emailAddress, String displayName) {

        Map<String, String> postParams = new HashMap<String, String>();
        postParams.put("user[display_name]", displayName);
        postParams.put("user[email]", emailAddress);

        int status = client.post("http://thoughtsquare.heroku.com/users/", postParams);

        return status == 201;

    }
}
