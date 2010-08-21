package com.thoughtsquare.activity;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import com.thoughtsquare.domain.User;
import com.thoughtsquare.service.LocationService;

public class LocationAutoUpdater implements LocationListener{
    private User user;
    private LocationService locationService;


    public LocationAutoUpdater(User user, LocationService locationService) {
        this.user = user;
        this.locationService = locationService;
    }

    public void onLocationChanged(Location location) {
        com.thoughtsquare.domain.Location resolvedLocation = locationService.findCurrentLocation(location);
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
