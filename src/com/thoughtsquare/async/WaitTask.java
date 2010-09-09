package com.thoughtsquare.async;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

public abstract class WaitTask<Result> extends AsyncTask<Void, Void, Result> {
    private ProgressDialog spinner;

    public WaitTask(Activity activity, String waitMessage) {
        spinner = new ProgressDialog(activity);
        spinner.setMessage(waitMessage + "...");
    }

    @Override
    protected final void onPreExecute() {
        spinner.show();
    }

    @Override
    protected final Result doInBackground(Void... stupidvoids) {
        return doStuff();
    }

    protected abstract Result doStuff();

    @Override
    protected final void onPostExecute(Result result) {
        spinner.dismiss();
        doAfter(result);
    }

    protected abstract void doAfter(Result result);


}