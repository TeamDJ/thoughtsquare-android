package com.thoughtsquare.db;

import android.content.Context;
import com.thoughtsquare.domain.Location;

public class LocationProvider extends DbProvider<Location> {
    public LocationProvider(Context context) {
        super(Location.class, context);
    }
}

