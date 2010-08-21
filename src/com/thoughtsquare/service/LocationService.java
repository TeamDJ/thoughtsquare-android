package com.thoughtsquare.service;

import com.thoughtsquare.domain.Location;

import java.util.ArrayList;
import java.util.List;

public class LocationService {
        public List<Location> getLocations() {
        List<Location> locations = new ArrayList<Location>();

        locations.add(new Location(2, "Brisbane", -27.467581, 153.027893));
        locations.add(new Location(3, "Sydney", -33.867138, 151.207108));
        locations.add(new Location(4, "Melbourne", -37.814251, 144.963165));
        locations.add(new Location(5, "Perth", -31.9554, 115.858589));

        return locations;
    }

    public Location findCurrentLocation(android.location.Location location) {

        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
