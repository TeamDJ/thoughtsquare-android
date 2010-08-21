package com.thoughtsquare.test;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.InstrumentationTestCase;
import com.thoughtsquare.domain.Location;
import com.thoughtsquare.domain.User;
import com.thoughtsquare.domain.UserProvider;

public class UserProviderTest extends InstrumentationTestCase {

    public void testSave(){
        Context context = getInstrumentation().getContext();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        UserProvider userProvider = new UserProvider(sharedPreferences, null, null);
        Location location = new Location(2, "Brisbane", 32.5677878, -122.454643, 100);


        User user = new User(userProvider, null, null, 5, "foo@bla.com", "bar", location);

        userProvider.saveUser(user);

        User savedUser = userProvider.getUser();
        assertEquals("foo@bla.com", savedUser.getEmail());
        assertEquals("bar", savedUser.getDisplayName());
        assertEquals(5, savedUser.getId().intValue());

        Location savedLocation = user.getCurrentLocation();
        assertEquals(2, savedLocation.getId());
        assertEquals("Brisbane", savedLocation.getTitle());
        assertEquals(32.5677878, savedLocation.getLatitude());
        assertEquals(-122.454643, savedLocation.getLongitude());
        assertEquals(100.0, savedLocation.getRadius());

    }

}
