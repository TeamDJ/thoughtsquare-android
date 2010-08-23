package com.thoughtsquare.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.thoughtsquare.R;
import com.thoughtsquare.async.RegisterUserTask;
import com.thoughtsquare.domain.User;
import com.thoughtsquare.domain.UserProvider;
import com.thoughtsquare.utility.AHTTPClient;
import com.thoughtsquare.utility.Config;
import com.thoughtsquare.utility.ConfigLoader;

import java.util.concurrent.ExecutionException;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class RegisterActivity extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        final Config config = new ConfigLoader().getConfig(this);
        final UserProvider userProvider = new UserProvider(getDefaultSharedPreferences(this), new AHTTPClient(), config);

        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String displayName = getTextFromTextBox(R.id.displayName);
                String emailAddress = getTextFromTextBox(R.id.emailAddress);

                final User user = userProvider.createUser(emailAddress, displayName);

                boolean registerSuccess = false;
                try {
                    registerSuccess = new RegisterUserTask(getContext()).execute(user).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                
                if (registerSuccess) {
                    Bundle extras = new Bundle();
                    extras.putString("displayName", displayName);
                    extras.putString("emailAddress", emailAddress);

                    Intent mIntent = new Intent();
                    mIntent.putExtras(extras);
                    setResult(RESULT_OK, mIntent);
                    finish();
                }

                //TODO: What to do when userService cannot register user?
            }

        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);    //To change body of overridden methods use File | Settings | File Templates.
    }

    private String getTextFromTextBox(int viewId) {
        return ((EditText) findViewById(viewId)).getText().toString();
    }

    private Context getContext() {
        return this;
    }
}
