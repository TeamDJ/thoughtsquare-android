package com.thoughtsquare.utility;

import org.json.JSONException;

public class JSONArray {
    private org.json.JSONArray jsonArray;

    public JSONArray(org.json.JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public JSONArray(String s){
        try {
            jsonArray = new org.json.JSONArray(s);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public Object get(int index) {
        try {
            return jsonArray.get(index);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public org.json.JSONObject getJSONObject(int index) {
        try {
            return jsonArray.getJSONObject(index);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
