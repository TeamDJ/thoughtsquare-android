package com.thoughtsquare.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;
import com.jayway.android.robotium.solo.Solo;
import com.thoughtsquare.R;
import com.thoughtsquare.activity.ThoughtSquareActivity;

public class ThoughtSquareActivityTest extends ActivityInstrumentationTestCase2<ThoughtSquareActivity> {
    private Solo solo;
    private ThoughtSquareActivity activity;

    public ThoughtSquareActivityTest() {
        super("com.thoughtsquare", ThoughtSquareActivity.class);
    }

    public void setUp() throws Exception {
        activity = getActivity();
        solo = new Solo(getInstrumentation(), activity);

    }

    // only works for a newly installed app that doesnt have user details saved
    public void testEverythingThatMatters() {
        solo.enterText(0, "Julian Oliver");
        solo.enterText(1, "joliver@thoughtworks.com");
        solo.clickOnButton("Register");

        solo.waitForText("Hello");
        assertEquals("Hello Julian Oliver", getText(R.id.welcome_label));
        assertEquals("Unknown", getText(R.id.current_location));

        solo.clickOnButton("Update Location");
        solo.clickInList(2); // Add Location...

        solo.waitForText("Add Location");
        solo.enterText(0, "Sydney");
        solo.enterText(1, "1");
        solo.enterText(2, "1");
        solo.clickOnButton("Add Location");

        solo.clickInList(2); // Sydney

        solo.waitForText("Hello");
        assertEquals("Sydney", getText(R.id.current_location));
    }

    private String getText(int viewId) {
        TextView textView = (TextView) activity.findViewById(viewId);
        String text = textView.getText().toString();
        return text;
    }

    @Override
    public void tearDown() throws Exception {
        try {
            solo.finalize();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        getActivity().finish();
        super.tearDown();
    }
}
    