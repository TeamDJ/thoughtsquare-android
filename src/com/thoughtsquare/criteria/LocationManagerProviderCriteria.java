package com.thoughtsquare.criteria;

import android.location.Criteria;

public class LocationManagerProviderCriteria extends Criteria {
    public LocationManagerProviderCriteria() {
        this.setAccuracy(Criteria.ACCURACY_FINE);
        this.setAltitudeRequired(false);
        this.setBearingRequired(false);
        this.setCostAllowed(false);
        this.setPowerRequirement(Criteria.POWER_MEDIUM);
        this.setSpeedRequired(false);
    }
}
