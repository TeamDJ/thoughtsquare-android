package com.thoughtworks.teamdj.thoughtsquare.com.thoughtworks.teamdj.thoughtsquare.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;
import com.thoughtworks.teamdj.thoughtsquare.R;
import com.thoughtworks.teamdj.thoughtsquare.ThoughtSquareActivity;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class com.thoughtworks.teamdj.thoughtsquare.com.thoughtworks.teamdj.thoughtsquare.test.ThoughtSquareActivityTest \
 * com.thoughtworks.teamdj.thoughtsquare.tests/android.test.InstrumentationTestRunner
 */
public class ThoughtSquareActivityTest extends ActivityInstrumentationTestCase2<ThoughtSquareActivity> {

    public ThoughtSquareActivityTest() {
        super("com.thoughtworks.teamdj.thoughtsquare", ThoughtSquareActivity.class);
    }

    public void testWelcomeTextIsPresent(){
        TextView textView = (TextView) getActivity().findViewById(R.id.welcome_label);
        assertEquals("Hello World, ThoughtSquareActivity", textView.getText());
    }



}
    