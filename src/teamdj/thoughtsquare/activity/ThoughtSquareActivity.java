package teamdj.thoughtsquare.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import teamdj.thoughtsquare.R;

import static teamdj.thoughtsquare.Preferences.DEFAULT;
import static teamdj.thoughtsquare.Preferences.DISPLAY_NAME;

public class ThoughtSquareActivity extends Activity {
    private static final int REGISTER_ACTIVITY = 0;
    private static final int UPDATE_LOCATION_ACTIVITY = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button updateLocation = (Button) findViewById(R.id.update_location);
        updateLocation.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getContext(), UpdateLocationActivity.class);
                startActivityForResult(i, UPDATE_LOCATION_ACTIVITY);
            }
        });

        String displayName = getSharedPreferences(DEFAULT, MODE_WORLD_WRITEABLE).getString(DISPLAY_NAME, "Stranger");
        if ("Stranger".equals(displayName)) {
            Intent i = new Intent(this, RegisterActivity.class);
            startActivityForResult(i, REGISTER_ACTIVITY);
        } else {
            //TODO what doest this MODE_WORLD_WRITEABLE mean ? should we be worried?
            greetUser(displayName);
        }
    }

    private Context getContext() {
        return this;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Bundle extras = intent.getExtras();
        switch (requestCode) {
            case REGISTER_ACTIVITY:
                greetUser(extras.getString("displayName"));
                break;
            case UPDATE_LOCATION_ACTIVITY:
                TextView currentLocation = (TextView)findViewById(R.id.current_location);
                currentLocation.setText(extras.getString("locationTitle"));
                break;
        }
    }

    private void greetUser(String displayName) {

        TextView textView = (TextView) findViewById(R.id.welcome_label);
        textView.setText("Hello " + displayName);
    }


}
