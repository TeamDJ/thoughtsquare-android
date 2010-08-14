package teamdj.thoughtsquare.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import teamdj.thoughtsquare.Preferences;
import teamdj.thoughtsquare.R;
import teamdj.thoughtsquare.service.UserService;
import teamdj.thoughtsquare.utility.AHTTPClient;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static teamdj.thoughtsquare.Preferences.*;
import static teamdj.thoughtsquare.Preferences.*;

public class RegisterActivity extends Activity {


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        final EditText displayNameField = (EditText) findViewById(R.id.displayName);
        final EditText emailAddressField = (EditText) findViewById(R.id.emailAddress);

        Button registerButton = (Button) findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String displayName = displayNameField.getText().toString();
                String emailAddress = emailAddressField.getText().toString();

                UserService service = new UserService(new AHTTPClient());
                if (service.register(emailAddress, displayName)) {
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

                //TODO: What to do when service cannot register user?
            }

        });
    }

    private Context getContext() {
        return this;
    }
}
