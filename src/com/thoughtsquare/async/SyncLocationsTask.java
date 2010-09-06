package com.thoughtsquare.async;

import android.content.Context;
import android.os.AsyncTask;
import com.thoughtsquare.db.LocationProvider;
import com.thoughtsquare.domain.Location;
import com.thoughtsquare.service.LocationService;

import java.util.List;

public class SyncLocationsTask extends AsyncTask<Void, Void, Void> {
    private Context context;

    public SyncLocationsTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        LocationService service = new LocationService(context);
        LocationProvider provider = new LocationProvider(context);

        List<Location> locations = service.getRemoteLocations();

        for (Location location : locations) {
            provider.storeIfNotExists(location);
        }

        return null;
    }
}
