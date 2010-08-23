package com.thoughtsquare.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import com.thoughtsquare.activity.RegisterActivity;
import com.thoughtsquare.domain.User;

public class RegisterUserTask extends AsyncTask<User, Void, Boolean> {
    private ProgressDialog spinner;

    public RegisterUserTask(Context context) {
        spinner = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        spinner.setMessage("Registering...");
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
    }
}