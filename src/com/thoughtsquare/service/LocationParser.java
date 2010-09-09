package com.thoughtsquare.service;

import com.thoughtsquare.domain.Location;
import com.thoughtsquare.utility.JSONArray;
import com.thoughtsquare.utility.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LocationParser  {
    public List<Location> parseLocations(String json) {
        ArrayList<Location> locations = new ArrayList<Location>();
        JSONArray array = new JSONArray(json);

        for(int i=0; i < array.length(); i++){
            JSONObject jsonLocation = array.getJSONObject(i).getJSONObject("location");
                Location location = new Location(
                        jsonLocation.getInt("id"),
                        jsonLocation.getString("title"),
                        jsonLocation.getDouble("latitude"),
                        jsonLocation.getDouble("longitude"),
                        0
                );
                locations.add(location);
        }

        return locations;
    }
}
