package com.thoughtsquare.async;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import com.thoughtsquare.activity.RegisterActivity;
import com.thoughtsquare.domain.User;

public class RegisterUserTask extends AsyncTask<User, Void, Boolean> {
    private ProgressDialog spinner;
    private RegisterActivity activity;

    public RegisterUserTask(RegisterActivity activity) {
        this.activity = activity;
        spinner = new ProgressDialog(activity);
        spinner.setMessage("Registering...");
    }

    @Override
    protected void onPreExecute() {
        spinner.show();
    }

    @Override
    protected Boolean doInBackground(User... users) {
        boolean success = true;
        for (User user : users) {
            if (!user.register()) {
                success = false;
            }
        }
        return success;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        spinner.dismiss();
        activity.onFinishRegisterTask();
    }
}