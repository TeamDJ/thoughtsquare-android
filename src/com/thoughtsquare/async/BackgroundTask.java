package com.thoughtsquare.async;

import android.os.AsyncTask;

public abstract class BackgroundTask extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... voids) {
        run();
        return null;
    }

    public abstract void run();
}
