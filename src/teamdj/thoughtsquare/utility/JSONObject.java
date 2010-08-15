package teamdj.thoughtsquare.utility;

import org.json.JSONArray;
import org.json.JSONException;

public class JSONObject extends org.json.JSONObject{
    private org.json.JSONObject jsonObject;

    public JSONObject(org.json.JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JSONObject(String s){
        try {
            jsonObject = new org.json.JSONObject(s);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public Object get(String s) {
        try {
            return jsonObject.get(s);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean getBoolean(String s) {
        try {
            return jsonObject.getBoolean(s);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public double getDouble(String s) {
        try {
            return jsonObject.getDouble(s);
        } catch (JSONException e) {
            throw new RuntimeException(e);

        }
    }

    public int getInt(String s) {
        try {
            return jsonObject.getInt(s);
        } catch (JSONException e) {
            throw new RuntimeException(e);

        }
    }

    public JSONArray getJSONArray(String s) {
        try {
            return jsonObject.getJSONArray(s);
        } catch (JSONException e) {
            throw new RuntimeException(e);

        }
    }

    public JSONObject getJSONObject(String s) {
        try {
            return new JSONObject(jsonObject.getJSONObject(s));
        } catch (JSONException e) {
            throw new RuntimeException(e);

        }
    }

    public long getLong(String s) {
        try {
            return jsonObject.getLong(s);
        } catch (JSONException e) {
            throw new RuntimeException(e);

        }
    }

    public String getString(String s) {
        try {
            return jsonObject.getString(s);
        } catch (JSONException e) {
            throw new RuntimeException(e);

        }
    }


}
