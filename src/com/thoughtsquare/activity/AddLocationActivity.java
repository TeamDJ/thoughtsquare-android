package com.thoughtsquare.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.thoughtsquare.R;
import com.thoughtsquare.async.WaitTask;
import com.thoughtsquare.domain.Location;
import com.thoughtsquare.service.LocationService;
import com.thoughtsquare.utility.AHTTPClient;
import com.thoughtsquare.utility.ConfigLoader;

import static java.lang.Double.valueOf;

public class AddLocationActivity extends Activity {
    private LocationService locationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_location);

        locationService = new LocationService(new ConfigLoader().getConfig(this), new AHTTPClient());

        Button addLocation = (Button) findViewById(R.id.addLocation);

        addLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String title = ((EditText) findViewById(R.id.title)).getText().toString();
                String latitude = ((EditText) findViewById(R.id.latitude)).getText().toString();
                String longitude = ((EditText) findViewById(R.id.longitude)).getText().toString();

                final Location location = new Location(0, title, valueOf(latitude), valueOf(longitude), 0);

                addLocation(location);
            }
        });
    }

    private void addLocation(final Location location) {
        new WaitTask<Location>(AddLocationActivity.this, "Adding location...") {
            protected Location doStuff() {
                return locationService.addLocation(location.getTitle(), location.getLatitude(), location.getLongitude());
            }
            protected void doAfter(Location location) {
                if (location != null) {
                    Intent mIntent = new Intent();
                    mIntent.putExtra("location",location);
                    setResult(RESULT_OK, mIntent);
                    finish();
                }
            }
        }.execute();
    }


}
