package teamdj.thoughtsquare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ThoughtSquareActivity extends Activity {
    private static final int REGISTER_ACTIVITY = 0;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
//
//        Intent i = new Intent(this, RegisterActivity.class);
//        startActivityForResult(i, REGISTER_ACTIVITY);
    }


}
