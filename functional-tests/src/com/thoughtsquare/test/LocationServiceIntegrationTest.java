package com.thoughtsquare.test;

import android.test.InstrumentationTestCase;
import com.thoughtsquare.service.LocationService;

public class LocationServiceIntegrationTest extends InstrumentationTestCase {
    public void testFindContainingLocationWhenContainingLocationExists() {
        android.location.Location brisbaneLocation = new android.location.Location("");
        brisbaneLocation.setLatitude(-27.465698);
        brisbaneLocation.setLongitude(153.027733);

        LocationService locationService = new LocationService(getInstrumentation().getContext());

        assertEquals("Brisbane", locationService.findContainingLocation(brisbaneLocation).getTitle());
    }

    public void testFindContainingLocationWhenContainingLocationDoesntExist() {
        android.location.Location canberraLocation = new android.location.Location("");
        canberraLocation.setLatitude(-35.244297);
        canberraLocation.setLongitude(149.047327);

        LocationService locationService = new LocationService(getInstrumentation().getContext());

        assertNull(locationService.findContainingLocation(canberraLocation));
    }
}
