package com.thoughtsquare.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.thoughtsquare.R;
import com.thoughtsquare.async.UpdateLocationTask;
import com.thoughtsquare.criteria.LocationManagerProviderCriteria;
import com.thoughtsquare.domain.Location;
import com.thoughtsquare.domain.User;
import com.thoughtsquare.domain.UserProvider;
import com.thoughtsquare.intent.LocationUpdateReceiver;
import com.thoughtsquare.intent.OnLocationUpdate;
import com.thoughtsquare.service.LocationService;
import com.thoughtsquare.service.NotificationService;
import com.thoughtsquare.utility.*;

import java.util.HashMap;
import java.util.Map;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.thoughtsquare.intent.IntentActions.LOCATION_UPDATED;
import static com.thoughtsquare.utility.ViewUtils.getLabel;
import static com.thoughtsquare.utility.ViewUtils.setLabel;

public class ThoughtSquareActivity extends Activity implements OnLocationUpdate {
    private static final int REGISTER_ACTIVITY = 0;
    private static final int UPDATE_LOCATION_ACTIVITY = 1;
    private UserProvider userProvider;
    private LocationService locationService;
    private LocationUpdateReceiver locationUpdateReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Config config = new ConfigLoader().getConfig(this);
        AHTTPClient httpClient = new AHTTPClient();
        SharedPreferences preferences = getDefaultSharedPreferences(this);

        userProvider = new UserProvider(preferences, httpClient, config);
        locationService = new LocationService(config, httpClient);

        setupUpdateLocationButton();

        if (userProvider.userExists()) {
            User user = userProvider.getUser();
            greetUser(user.getDisplayName());
            if (user.currentLocationIsKnown()) {
                showLocation(user.getCurrentLocation());
            }
        } else {
            startRegisterActivity();
        }

        setupAutoLocationUpdater();
        setupShoutButton();

        startService(new Intent(this, NotificationService.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (intent != null) {
            Bundle extras = intent.getExtras();
            switch (requestCode) {
                case REGISTER_ACTIVITY:
                    greetUser(extras.getString("displayName"));
                    break;
                case UPDATE_LOCATION_ACTIVITY:
                    Location location = extras.getParcelable("location");
                    showLocation(location);
                    updateUserLocation(location);
                    break;
            }
        }
    }

    private void updateUserLocation(Location location) {
        Map<User, Location> userLocation = new HashMap<User, Location>();
        userLocation.put(userProvider.getUser(), location);
        new UpdateLocationTask().execute(userLocation);
    }


    private void setupAutoLocationUpdater() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //TODO what provider should we be using and how long should we be polling - config?
        String bestProvider = locationManager.getBestProvider(new LocationManagerProviderCriteria(), true);
        locationManager.requestLocationUpdates(bestProvider, 0, 0,
                new LocationAutoUpdater(new IntentBuilder(), this, userProvider.getUser(), locationService));

        // update this view when location changes
        locationUpdateReceiver = new LocationUpdateReceiver(this);
        this.registerReceiver(locationUpdateReceiver, new IntentFilter(LOCATION_UPDATED));
    }


    private void startRegisterActivity() {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivityForResult(i, REGISTER_ACTIVITY);
    }

    private void setupUpdateLocationButton() {
        Button updateLocation = (Button) findViewById(R.id.update_location);
        updateLocation.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(ThoughtSquareActivity.this, UpdateLocationActivity.class);
                startActivityForResult(i, UPDATE_LOCATION_ACTIVITY);
            }
        });
    }

    private void setupShoutButton() {
        Button shoutButton = (Button) findViewById(R.id.shout);
        shoutButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String locationTitle = getLabel(ThoughtSquareActivity.this, R.id.current_location);

                Intent intent = new Intent(ThoughtSquareActivity.this, ShoutActivity.class);
                intent.putExtra("locationTitle", locationTitle);
                startActivity(intent);
            }
        });
    }

    private void greetUser(String displayName) {
        setLabel(this, R.id.welcome_label, "Hello " + displayName);
    }

    private void showLocation(Location location) {
        setLabel(this, R.id.current_location, location.getTitle());
    }


    public void update(Location location) {
        showLocation(location);
    }

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(locationUpdateReceiver);
        super.onDestroy();
    }
}
