package com.thoughtsquare.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import com.thoughtsquare.domain.User;
import com.thoughtsquare.intent.IntentActions;
import com.thoughtsquare.service.LocationsProvider;
import com.thoughtsquare.utility.IntentBuilder;

import static com.thoughtsquare.intent.IntentActions.LOCATION_UPDATED;

public class LocationAutoUpdater implements LocationListener{
    private IntentBuilder intentBuilder;
    private Context context;
    private User user;
    private LocationsProvider locationsProvider;


    public LocationAutoUpdater(IntentBuilder intentBuilder, Context context, User user, LocationsProvider locationsProvider) {
        this.intentBuilder = intentBuilder;
        this.context = context;
        this.user = user;
        this.locationsProvider = locationsProvider;
    }

    public void onLocationChanged(Location location) {
        //TODO there may be a better way of doing this, eg. with proximity alerts
        com.thoughtsquare.domain.Location resolvedLocation = locationsProvider.findContainingLocation(location);
        if(resolvedLocation != null){
            Intent intent = intentBuilder.withAction(LOCATION_UPDATED).withParcelable("location", resolvedLocation).build();
            context.sendBroadcast(intent);

            user.updateLocation(resolvedLocation);
        }

    }

    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    public void onProviderEnabled(String s) {

    }

    public void onProviderDisabled(String s) {

    }
}
