package com.thoughtsquare.utility;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class JSONArray {
    private org.json.JSONArray jsonArray;

    public JSONArray(String s){
        if(s==null)throw new IllegalArgumentException("s cannot be null");
        try {
            this.jsonArray = new org.json.JSONArray(s);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public JSONArray(org.json.JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public JSONObject getJSONObject(int i){
        try {
            return new JSONObject(jsonArray.getJSONObject(i));
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

    public int length(){
        return jsonArray.length();
    }

    public List<JSONObject> getJSONObjects() {
        List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
        for (int i = 0; i <= jsonArray.length(); i++) {
            if (!jsonArray.isNull(i)) {
                try {
                    jsonObjects.add(new JSONObject(jsonArray.getJSONObject(i)));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return jsonObjects;
    }
}
