package com.thoughtsquare.utility;

import org.json.JSONException;

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
}
