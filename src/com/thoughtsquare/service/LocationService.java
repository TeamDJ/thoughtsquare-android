package com.thoughtsquare.service;

import com.thoughtsquare.domain.AddLocation;
import com.thoughtsquare.domain.Location;

import java.util.ArrayList;
import java.util.List;

public class LocationService {
    private static final int CITY_RADIUS = 100000;

    public List<Location> getLocations() {
        List<Location> locations = new ArrayList<Location>();

        locations.add(new Location(2, "Brisbane", -27.467581, 153.027893, CITY_RADIUS));
        locations.add(new Location(3, "Sydney", -33.867138, 151.207108, CITY_RADIUS));
        locations.add(new Location(4, "Melbourne", -37.814251, 144.963165, CITY_RADIUS));
        locations.add(new Location(5, "Perth", -31.9554, 115.858589, CITY_RADIUS));
        locations.add(new AddLocation());

        return locations;
    }


    public Location findContainingLocation(android.location.Location location) {
        for(Location storedLocation : getLocations()){
            if(storedLocation.contains(location)){
                return storedLocation;
            }
        }
        return null;
    }

}
