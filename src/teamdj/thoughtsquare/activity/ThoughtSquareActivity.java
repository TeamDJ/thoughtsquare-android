package teamdj.thoughtsquare.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import teamdj.thoughtsquare.R;

import static teamdj.thoughtsquare.Preferences.DEFAULT;
import static teamdj.thoughtsquare.Preferences.DISPLAY_NAME;

public class ThoughtSquareActivity extends Activity {
    private static final int REGISTER_ACTIVITY = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        String displayName = getSharedPreferences(DEFAULT, MODE_WORLD_WRITEABLE).getString(DISPLAY_NAME, "Stranger");
        if ("Stranger".equals(displayName)) {
            Intent i = new Intent(this, RegisterActivity.class);
            startActivityForResult(i, REGISTER_ACTIVITY);
        } else {
            //TODO what doest this MODE_WORLD_WRITEABLE mean ? should we be worried?
            greetUser(displayName);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Bundle extras = intent.getExtras();
        switch(requestCode) {
            case REGISTER_ACTIVITY:
                greetUser(extras.getString("display"));
                break;
        }
    }

    private void greetUser(String displayName){

        TextView textView = (TextView) findViewById(R.id.welcome_label);
        textView.setText("Hello " + displayName);
    }


}
