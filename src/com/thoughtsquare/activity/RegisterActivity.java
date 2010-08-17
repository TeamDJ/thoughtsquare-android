package com.thoughtsquare.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.thoughtsquare.R;
import com.thoughtsquare.domain.User;
import com.thoughtsquare.domain.UserProvider;
import com.thoughtsquare.utility.AHTTPClient;
import com.thoughtsquare.utility.Config;
import com.thoughtsquare.utility.ConfigLoader;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.thoughtsquare.Preferences.*;

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

                if (user.register()) {
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

    private String getTextFromTextBox(int viewId) {
        return ((EditText) findViewById(viewId)).getText().toString();
    }

    private Context getContext() {
        return this;
    }
}
