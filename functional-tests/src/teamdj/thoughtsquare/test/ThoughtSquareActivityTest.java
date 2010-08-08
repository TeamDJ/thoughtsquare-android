package teamdj.thoughtsquare.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;
import teamdj.thoughtsquare.R;
import teamdj.thoughtsquare.ThoughtSquareActivity;


public class ThoughtSquareActivityTest extends ActivityInstrumentationTestCase2<ThoughtSquareActivity> {

    public ThoughtSquareActivityTest() {
        super("teamdj.thoughtsquare", ThoughtSquareActivity.class);
    }

    public void testWelcomeTextIsPresent(){
        TextView textView = (TextView) getActivity().findViewById(R.id.welcome_label);
        assertEquals("Hello World, ThoughtSquareActivity", textView.getText());
    }



}
    