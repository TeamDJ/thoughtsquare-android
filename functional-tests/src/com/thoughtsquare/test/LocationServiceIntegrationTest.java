package com.thoughtsquare.test;

import android.test.InstrumentationTestCase;
import com.thoughtsquare.service.LocationService;
import com.thoughtsquare.utility.AHTTPClient;
import com.thoughtsquare.utility.Config;
import com.thoughtsquare.utility.ConfigLoader;

public class LocationServiceIntegrationTest extends InstrumentationTestCase {
    private LocationService locationService;

    @Override
    public void setUp(){
        Config config = new ConfigLoader().getConfig(getInstrumentation().getContext());
        locationService = new LocationService(config, new AHTTPClient());
    }

    public void testFindContainingLocationWhenContainingLocationExists() {
        android.location.Location brisbaneLocation = new android.location.Location("");
        brisbaneLocation.setLatitude(-27.465698);
        brisbaneLocation.setLongitude(153.027733);

        assertEquals("Brisbane", locationService.findContainingLocation(brisbaneLocation).getTitle());
    }

    public void testFindContainingLocationWhenContainingLocationDoesntExist() {
        android.location.Location canberraLocation = new android.location.Location("");
        canberraLocation.setLatitude(-35.244297);
        canberraLocation.setLongitude(149.047327);

        assertNull(locationService.findContainingLocation(canberraLocation));
    }
}
