package com.thoughtsquare.test;

import android.content.Context;
import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;
import com.thoughtsquare.db.LocationProvider;
import com.thoughtsquare.domain.Location;
import com.thoughtsquare.service.LocationService;
import com.thoughtsquare.utility.AHTTPClient;
import com.thoughtsquare.utility.Config;
import com.thoughtsquare.utility.ConfigLoader;

public class LocationServiceIntegrationTest extends AndroidTestCase {
    private static final int CITY_RADIUS = 100000;

    public void testFindContainingLocationWhenContainingLocationExists() {
        android.location.Location brisbaneLocation = new android.location.Location("");
        brisbaneLocation.setLatitude(-27.465698);
        brisbaneLocation.setLongitude(153.027733);

        Context context = getContext();

        Config config = new ConfigLoader().getConfig(context);
        AHTTPClient httpClient = new AHTTPClient();
        LocationProvider locationProvider = new LocationProvider(context);

        locationProvider.store(new Location(1, "Brisbane", -27.467581, 153.027893, CITY_RADIUS));

        LocationService locationService = new LocationService(config, httpClient, locationProvider);

        assertEquals("Brisbane", locationService.findContainingLocation(brisbaneLocation).getTitle());
    }

    public void testFindContainingLocationWhenContainingLocationDoesntExist() {
        android.location.Location canberraLocation = new android.location.Location("");
        canberraLocation.setLatitude(-35.244297);
        canberraLocation.setLongitude(149.047327);

        LocationService locationService = new LocationService(getContext());

        assertNull(locationService.findContainingLocation(canberraLocation));
    }
}
