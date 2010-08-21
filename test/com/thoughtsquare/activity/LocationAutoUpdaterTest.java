package com.thoughtsquare.activity;

import com.thoughtsquare.domain.Location;
import com.thoughtsquare.domain.User;
import com.thoughtsquare.service.LocationProvider;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class LocationAutoUpdaterTest{
    private User user;
    private LocationProvider locationProvider;
    private LocationAutoUpdater locationAutoUpdater;

    @Before
    public void setup() {
        user = mock(User.class);
        locationProvider = mock(LocationProvider.class);
        locationAutoUpdater = new LocationAutoUpdater(user, locationProvider);
    }

    @Test
    public void shouldUpdateUsersLocationWhenLocationIsFound() {
        android.location.Location location = mock(android.location.Location.class);
        Location foundLocation = mock(Location.class);
        when(locationProvider.getNearestLocation(location)).thenReturn(foundLocation);

        locationAutoUpdater.onLocationChanged(location);

        verify(user).updateLocation(foundLocation);
    }

    @Test
    public void shouldNotUpdateUsersLocationWhenLocationIsNotKnown() {
        android.location.Location location = mock(android.location.Location.class);
        when(locationProvider.getNearestLocation(location)).thenReturn(null);

        locationAutoUpdater.onLocationChanged(location);

        verify(user, never()).updateLocation(any(Location.class));
    }
}
