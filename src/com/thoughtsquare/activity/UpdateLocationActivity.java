package com.thoughtsquare.activity;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.thoughtsquare.R;
import com.thoughtsquare.domain.AddLocation;
import com.thoughtsquare.domain.Location;
import com.thoughtsquare.service.LocationService;
import com.thoughtsquare.utility.AHTTPClient;
import com.thoughtsquare.utility.ConfigLoader;

import java.util.List;

public class UpdateLocationActivity extends ListActivity {
    private static final int ADD_LOCATION_ACTIVITY = 0;

    private List<Location> locations;
    private LocationService service;
    private LocationAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        service = new LocationService(this);
        locations = service.getLocations();
        listAdapter = new LocationAdapter();

        setContentView(R.layout.update_location);
        setListAdapter(listAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Location location = locations.get(position);

        if (location instanceof AddLocation) {
            Intent i = new Intent(getContext(), AddLocationActivity.class);
            startActivityForResult(i, ADD_LOCATION_ACTIVITY);
        } else {
            Bundle extras = new Bundle();
            extras.putParcelable("location", location);

            Intent mIntent = new Intent();
            mIntent.putExtras(extras);
            setResult(RESULT_OK, mIntent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        Bundle extras = null;
        if (intent != null) {
            extras = intent.getExtras();
        }

        switch (requestCode) {
            case ADD_LOCATION_ACTIVITY:
                Location location = extras.getParcelable("location");
                listAdapter.insert(location, locations.size() - 1);
                break;
        }
    }

    class LocationAdapter extends ArrayAdapter<Location> {
        LocationAdapter() {
            super(UpdateLocationActivity.this, android.R.layout.simple_list_item_1, UpdateLocationActivity.this.locations);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

            TextView text = (TextView) row.findViewById(android.R.id.text1);
            text.setText(locations.get(position).getTitle());

            return row;
        }
    }

    private Context getContext() {
        return this;
    }
}
