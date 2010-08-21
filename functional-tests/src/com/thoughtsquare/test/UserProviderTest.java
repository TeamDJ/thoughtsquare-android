package com.thoughtsquare.test;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.InstrumentationTestCase;
import com.thoughtsquare.domain.User;
import com.thoughtsquare.domain.UserProvider;

public class UserProviderTest extends InstrumentationTestCase {

    public void testSave(){
        Context context = getInstrumentation().getContext();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        UserProvider userProvider = new UserProvider(sharedPreferences, null, null);

        User user = new User(userProvider, null, null, 5, "foo@bla.com", "bar");
        userProvider.saveUser(user);

        User foundUser = userProvider.getUser();
        assertEquals("foo@bla.com", foundUser.getEmail());
        assertEquals("bar", foundUser.getDisplayName());
        assertEquals(5, foundUser.getId().intValue());
    }

}
