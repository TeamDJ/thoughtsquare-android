package com.thoughtworks.teamdj.thoughtsquare;

import android.test.ActivityInstrumentationTestCase2;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class com.thoughtworks.teamdj.thoughtsquare.ThoughtSquareActivityTest \
 * com.thoughtworks.teamdj.thoughtsquare.tests/android.test.InstrumentationTestRunner
 */
public class ThoughtSquareActivityTest extends ActivityInstrumentationTestCase2<ThoughtSquareActivity> {

    public ThoughtSquareActivityTest() {
        super("com.thoughtworks.teamdj.thoughtsquare", ThoughtSquareActivity.class);
    }

}
