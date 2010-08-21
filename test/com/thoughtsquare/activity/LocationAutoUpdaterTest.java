package com.thoughtsquare.activity;

import com.thoughtsquare.domain.Location;
import com.thoughtsquare.domain.User;
import com.thoughtsquare.service.LocationService;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class LocationAutoUpdaterTest{
    private User user;
    private LocationService locationService;
    private LocationAutoUpdater locationAutoUpdater;

    @Before
    public void setup() {
        user = mock(User.class);
        locationService = mock(LocationService.class);
        locationAutoUpdater = new LocationAutoUpdater(user, locationService);
    }

    @Test
    public void shouldUpdateUsersLocationWhenLocationIsFound() {
        android.location.Location location = mock(android.location.Location.class);
        Location foundLocation = mock(Location.class);
        when(locationService.findCurrentLocation(location)).thenReturn(foundLocation);

        locationAutoUpdater.onLocationChanged(location);

        verify(user).updateLocation(foundLocation);
    }

    @Test
    public void shouldNotUpdateUsersLocationWhenLocationIsNotKnown() {
        android.location.Location location = mock(android.location.Location.class);
        when(locationService.findCurrentLocation(location)).thenReturn(null);

        locationAutoUpdater.onLocationChanged(location);

        verify(user, never()).updateLocation(any(Location.class));
    }
}
