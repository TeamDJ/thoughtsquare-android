package teamdj.thoughtsquare.utility;

import org.json.JSONObject;

public class AHTTPResponse {
    private int responseStatus;
    private String responseBody;

    public AHTTPResponse(int responseStatus, String responseBody) {
        this.responseStatus = responseStatus;
        this.responseBody = responseBody;
    }

    public int getResponseStatus() {
        return responseStatus;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public JSONObject getJSONResponse() {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }
}
