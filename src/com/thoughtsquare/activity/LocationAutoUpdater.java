package com.thoughtsquare.activity;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import com.thoughtsquare.domain.User;
import com.thoughtsquare.service.LocationProvider;

public class LocationAutoUpdater implements LocationListener{
    private User user;
    private LocationProvider locationProvider;


    public LocationAutoUpdater(User user, LocationProvider locationProvider) {
        this.user = user;
        this.locationProvider = locationProvider;
    }

    public void onLocationChanged(Location location) {
        com.thoughtsquare.domain.Location resolvedLocation = locationProvider.getNearestLocation(location);
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
