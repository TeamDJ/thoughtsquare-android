package com.thoughtsquare.service;

import android.content.Context;
import com.thoughtsquare.db.LocationProvider;
import com.thoughtsquare.domain.AddLocation;
import com.thoughtsquare.domain.Location;
import com.thoughtsquare.utility.*;
import org.apache.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.valueOf;

public class LocationService {
    private static final int CITY_RADIUS = 100000;

    private Config config;
    private AHTTPClient httpClient;
    private LocationProvider provider;

    public LocationService(Context context) {
        this.httpClient = new AHTTPClient();
        this.config = new ConfigLoader().getConfig(context);
        this.provider = new LocationProvider(context);
    }

    public LocationService(Config config, AHTTPClient httpClient, LocationProvider provider) {
        this.config = config;
        this.httpClient = httpClient;
        this.provider = provider;
    }

    public List<Location> getLocations() {
        List<Location> locations = new ArrayList<Location>();

        locations.addAll(provider.findAll());
        locations.add(new AddLocation());

        return locations;
    }

    public Location findContainingLocation(android.location.Location location) {
        for (Location storedLocation : getLocations()) {
            if (storedLocation.contains(location)) {
                return storedLocation;
            }
        }
        return null;
    }

    public Location addLocation(String title, double latitude, double longitude) {
        Map<String, String> postParams = new HashMap<String, String>();
        postParams.put("location[title]", title);
        postParams.put("location[latitude]", valueOf(latitude));
        postParams.put("location[longitude]", valueOf(longitude));

        AHTTPResponse response = httpClient.post(config.getServerBaseURL() + "/locations.json", postParams);

        if (response.getResponseStatus() == HttpStatus.SC_CREATED) {
            int id = response.getJSONResponse().getJSONObject("location").getInt("id");
            Location location = new Location(id, title, latitude, longitude, CITY_RADIUS);
            provider.store(location);
            return location;
        }
        return null;
    }

    public List<Location> getRemoteLocations() {
        AHTTPResponse response = httpClient.get(config.getServerBaseURL() + "/locations.json");
        List<JSONObject> jsonObjects = response.getJSONArray().getJSONObjects();
        List<Location> locations = new ArrayList<Location>();

        for (JSONObject jsonObject : jsonObjects) {
            Location location = new Location(jsonObject);
            locations.add(location);
        }

        return locations;
    }
}
