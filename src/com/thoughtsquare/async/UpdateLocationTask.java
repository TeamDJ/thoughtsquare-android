package com.thoughtsquare.async;

import android.os.AsyncTask;
import com.thoughtsquare.domain.Location;
import com.thoughtsquare.domain.User;

import java.util.Map;

public class UpdateLocationTask extends AsyncTask<Map<User, Location>, Integer, Boolean> {
    @Override
    protected Boolean doInBackground(Map<User, Location>... maps) {
        boolean success = false;
        for (Map.Entry entry : maps[0].entrySet()) {
            User user = (User) entry.getKey();
            Location location = (Location) entry.getValue();

            if (user != null && location != null && user.updateLocation(location)) {
                success = true;
            }
        }
        return success;
    }
}
