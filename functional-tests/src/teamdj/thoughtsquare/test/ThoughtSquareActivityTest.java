package teamdj.thoughtsquare.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;
//import com.jayway.android.robotium.solo.Solo;
import com.jayway.android.robotium.solo.Solo;
import teamdj.thoughtsquare.R;
import teamdj.thoughtsquare.ThoughtSquareActivity;


public class ThoughtSquareActivityTest extends ActivityInstrumentationTestCase2<ThoughtSquareActivity> {

    private Solo solo;


    public ThoughtSquareActivityTest() {
        super("teamdj.thoughtsquare", ThoughtSquareActivity.class);
    }


    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }


    public void testWelcomeTextIsPresent() {
        solo.assertCurrentActivity("Expected ThoughtSquareActivity activity", "ThoughtSquareActivity");


        TextView textView = (TextView) getActivity().findViewById(R.id.welcome_label);
        assertEquals("Hello World, ThoughtSquareActivity", textView.getText().toString());
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
    