package com.thoughtsquare.async;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import com.thoughtsquare.activity.AddLocationActivity;
import com.thoughtsquare.domain.Location;
import com.thoughtsquare.service.LocationService;

public class AddLocationTask extends AsyncTask<Location, Void, Location> {
    private AddLocationActivity activity;
    private LocationService service;
    private ProgressDialog spinner;

    public AddLocationTask(AddLocationActivity activity, LocationService service) {
        this.activity = activity;
        this.service = service;

        spinner = new ProgressDialog(activity);
        spinner.setMessage("Adding location...");
    }

    @Override
    protected void onPreExecute() {
        spinner.show();
    }

    @Override
    protected Location doInBackground(Location... locations) {
        Location location = locations[0];
        return service.addLocation(location.getTitle(), location.getLatitude(), location.getLongitude());
    }

    @Override
    protected void onPostExecute(Location aLocation) {
        spinner.dismiss();
        activity.onFinishTask();
    }
}
