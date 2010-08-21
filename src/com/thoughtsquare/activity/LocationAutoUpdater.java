package com.thoughtsquare.activity;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import com.thoughtsquare.domain.User;
import com.thoughtsquare.service.LocationsProvider;

public class LocationAutoUpdater implements LocationListener{
    private User user;
    private LocationsProvider locationsProvider;


    public LocationAutoUpdater(User user, LocationsProvider locationsProvider) {
        this.user = user;
        this.locationsProvider = locationsProvider;
    }

    public void onLocationChanged(Location location) {
        com.thoughtsquare.domain.Location resolvedLocation = locationsProvider.findContainingLocation(location);
        if(resolvedLocation != null){
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
