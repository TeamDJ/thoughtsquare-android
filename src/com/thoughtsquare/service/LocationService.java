package com.thoughtsquare.service;

import android.content.Context;
import com.thoughtsquare.domain.AddLocation;
import com.thoughtsquare.domain.Location;
import com.thoughtsquare.utility.AHTTPClient;
import com.thoughtsquare.utility.AHTTPResponse;
import com.thoughtsquare.utility.Config;
import com.thoughtsquare.utility.ConfigLoader;
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

    public LocationService(Config config, AHTTPClient httpClient) {
        this.config = config;
        this.httpClient = httpClient;
    }

    public List<Location> getLocations() {
        List<Location> locations = new ArrayList<Location>();

        locations.add(new Location(1, "Brisbane", -27.467581, 153.027893, CITY_RADIUS));
        locations.add(new Location(2, "Sydney", -33.867138, 151.207108, CITY_RADIUS));
        locations.add(new Location(3, "Melbourne", -37.814251, 144.963165, CITY_RADIUS));
        locations.add(new Location(4, "Perth", -31.9554, 115.858589, CITY_RADIUS));
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
            return new Location(id, title, latitude, longitude, CITY_RADIUS);
        }
        return null;
    }
}
