package com.thoughtsquare.utility;


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
        return new JSONObject(responseBody);
    }

    public JSONArray getJSONArray() {
        return new JSONArray(responseBody);
    }
}
