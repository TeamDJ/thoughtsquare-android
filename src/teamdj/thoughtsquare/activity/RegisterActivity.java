package teamdj.thoughtsquare.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import teamdj.thoughtsquare.R;
import teamdj.thoughtsquare.builder.UserBuilder;
import teamdj.thoughtsquare.domain.User;
import teamdj.thoughtsquare.service.UserService;
import teamdj.thoughtsquare.utility.AHTTPClient;
import teamdj.thoughtsquare.utility.Config;
import teamdj.thoughtsquare.utility.ConfigLoader;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static teamdj.thoughtsquare.Preferences.*;

public class RegisterActivity extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        Config config = new ConfigLoader().getConfig(this);
        final UserService userService = new UserService(config, new AHTTPClient(), new UserBuilder());

        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String displayName = getTextFromTextBox(R.id.displayName);
                String emailAddress = getTextFromTextBox(R.id.emailAddress);

                User user = userService.register(emailAddress, displayName);

                if (user != null) {
                    Bundle extras = new Bundle();
                    extras.putString("displayName", displayName);
                    extras.putString("emailAddress", emailAddress);

                    getDefaultSharedPreferences(getContext()).edit().putString(DISPLAY_NAME, displayName).commit();
                    getDefaultSharedPreferences(getContext()).edit().putString(USERNAME, emailAddress).commit();

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
