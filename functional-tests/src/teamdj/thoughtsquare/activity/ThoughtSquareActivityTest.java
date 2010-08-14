package teamdj.thoughtsquare.activity;

import android.os.Bundle;
import android.preference.Preference;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.TextView;
//import com.jayway.android.robotium.solo.Solo;
import com.jayway.android.robotium.solo.Solo;
import teamdj.thoughtsquare.Preferences;
import teamdj.thoughtsquare.R;
import teamdj.thoughtsquare.activity.ThoughtSquareActivity;

import static teamdj.thoughtsquare.Preferences.*;


public class ThoughtSquareActivityTest extends ActivityInstrumentationTestCase2<ThoughtSquareActivity> {

    private Solo solo;
    private ThoughtSquareActivity activity;


    public ThoughtSquareActivityTest() {
        super("teamdj.thoughtsquare", ThoughtSquareActivity.class);
    }


    public void setUp() throws Exception {
        activity = getActivity();
        solo = new Solo(getInstrumentation(), activity);
       
    }

    public void testAAA(){


    }


    public void testRegistration() {
        solo.waitForText("TW Email");
       
        solo.enterText(0, "Julian Oliver");
        solo.enterText(1, "joliver@thoughtworks.com");
        solo.clickOnButton("Register");

        TextView textView = (TextView) activity.findViewById(R.id.welcome_label);
        assertEquals("Hello Julian Oliver", textView.getText().toString());



    }


    public void testAlreadyRegistered(){
        getInstrumentation().getContext().getSharedPreferences(DEFAULT, 2).edit().putString(Preferences.DISPLAY_NAME, "Meow");

        getInstrumentation().callActivityOnResume(activity);

        TextView textView = (TextView) activity.findViewById(R.id.welcome_label);
        assertEquals("Hello Meow", textView.getText().toString());

        solo.assertCurrentActivity("Expected ThoughtSquareActivity", ThoughtSquareActivity.class);


    }





    @Override
    public void tearDown() throws Exception {

        getActivity().getSharedPreferences(DEFAULT, 2).edit().putString(Preferences.DISPLAY_NAME, null);
        try {
            solo.finalize();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        getActivity().finish();
        super.tearDown();
    }
}
    