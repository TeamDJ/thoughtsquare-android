package com.thoughtsquare.service;

import com.thoughtsquare.domain.Location;

import java.util.ArrayList;
import java.util.List;

public class MockLocationService implements LocationService {
    public List<Location> getLocations() {
        List<Location> locations = new ArrayList<Location>();

        locations.add(new Location(2, "Brisbane", -27.467581F, 153.027893F));
        locations.add(new Location(3, "Sydney", -33.867138F, 151.207108F));
        locations.add(new Location(4, "Melbourne", -37.814251F, 144.963165F));
        locations.add(new Location(5, "Perth", -31.9554F, 115.858589F));

        return locations;
    }
}
