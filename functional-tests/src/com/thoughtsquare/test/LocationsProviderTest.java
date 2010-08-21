package com.thoughtsquare.test;

import android.test.InstrumentationTestCase;
import com.thoughtsquare.domain.Location;
import com.thoughtsquare.service.LocationsProvider;

import java.util.List;

public class LocationsProviderTest extends InstrumentationTestCase {


    public void testFindContainingLocationWhenContainingLocationExists(){
        android.location.Location brisbaneLocation = new android.location.Location("");
        brisbaneLocation.setLatitude(-27.465698);
        brisbaneLocation.setLongitude(153.027733);

        LocationsProvider locationsProvider = new LocationsProvider();

        assertEquals("Brisbane", locationsProvider.findContainingLocation(brisbaneLocation).getTitle());
    }

    public void testFindContainingLocationWhenContainingLocationDoesntExist(){
        android.location.Location canberraLocation = new android.location.Location("");
        canberraLocation.setLatitude(-35.244297);
        canberraLocation.setLongitude(149.047327);

        LocationsProvider locationsProvider = new LocationsProvider();

        assertNull(locationsProvider.findContainingLocation(canberraLocation));
    }





}
