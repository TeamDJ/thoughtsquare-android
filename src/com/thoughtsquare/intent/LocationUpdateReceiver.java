package com.thoughtsquare.intent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.thoughtsquare.domain.Location;

public class LocationUpdateReceiver extends BroadcastReceiver{
    private OnLocationUpdate onLocationUpdate;

    public LocationUpdateReceiver(OnLocationUpdate onLocationUpdate) {
        this.onLocationUpdate = onLocationUpdate;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Location location = intent.getParcelableExtra("location");
        onLocationUpdate.update(location);
    }
}
