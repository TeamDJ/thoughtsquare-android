package com.thoughtsquare.async;

import android.os.AsyncTask;
import com.thoughtsquare.domain.User;

public class RegisterUserTask extends AsyncTask<User, Integer, Boolean> {
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
}