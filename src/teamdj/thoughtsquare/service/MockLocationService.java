package teamdj.thoughtsquare.service;

import teamdj.thoughtsquare.domain.Location;

import java.util.ArrayList;
import java.util.List;

public class MockLocationService implements LocationService {
    public List<Location> getLocations() {
        List<Location> locations = new ArrayList<Location>();

        locations.add(new Location(1, "Brisbane", 0, 0));
        locations.add(new Location(2, "Sydney", 0, 0));
        locations.add(new Location(3, "Melbourne", 0, 0));
        locations.add(new Location(4, "Perth", 0, 0));

        return locations;
    }
}
