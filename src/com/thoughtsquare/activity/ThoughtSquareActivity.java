package com.thoughtsquare.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.thoughtsquare.R;
import com.thoughtsquare.domain.Location;
import com.thoughtsquare.domain.User;
import com.thoughtsquare.domain.UserProvider;
import com.thoughtsquare.utility.AHTTPClient;
import com.thoughtsquare.utility.ConfigLoader;

import static android.preference.PreferenceManager.*;

public class ThoughtSquareActivity extends Activity {
    private static final int REGISTER_ACTIVITY = 0;
    private static final int UPDATE_LOCATION_ACTIVITY = 1;
    private UserProvider userProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        userProvider = new UserProvider(getDefaultSharedPreferences(this), new AHTTPClient(), new ConfigLoader().getConfig(this));

        setupUpdateLocationButton();

        if (userProvider.userExists()) {
            User user = userProvider.getUser();
            greetUser(user.getDisplayName());
            if(user.currentLocationIsKnown()){
                showLocation(user.getCurrentLocation());
            }
        } else {
            startRegisterActivity();
        }
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
                Location location = extras.getParcelable("location");
                showLocation(location);
                userProvider.getUser().updateLocation(location);
                break;
        }
    }

    private Context getContext() {
        return this;
    }


    private void startRegisterActivity() {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivityForResult(i, REGISTER_ACTIVITY);
    }

    private void setupUpdateLocationButton() {
        Button updateLocation = (Button) findViewById(R.id.update_location);
        updateLocation.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getContext(), UpdateLocationActivity.class);
                startActivityForResult(i, UPDATE_LOCATION_ACTIVITY);
            }
        });
    }

    private void greetUser(String displayName) {

        TextView textView = (TextView) findViewById(R.id.welcome_label);
        textView.setText("Hello " + displayName);
    }

    private void showLocation(Location location) {
        TextView currentLocation = (TextView)findViewById(R.id.current_location);
        currentLocation.setText(location.getTitle());
    }


}
