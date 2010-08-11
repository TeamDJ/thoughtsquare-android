package teamdj.thoughtsquare.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import teamdj.thoughtsquare.Preferences;
import teamdj.thoughtsquare.R;
import teamdj.thoughtsquare.service.RegisterService;
import teamdj.thoughtsquare.utility.AHTTPClient;

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

                RegisterService service = new RegisterService(new AHTTPClient());
                if (service.register(emailAddress, displayName)) {
                    Bundle extras = new Bundle();
                    extras.putString("displayName", displayName);
                    extras.putString("emailAddress", emailAddress);

                    getSharedPreferences(DEFAULT, MODE_WORLD_WRITEABLE).edit().putString(DISPLAY_NAME, displayName).commit();
                    getSharedPreferences(DEFAULT, MODE_WORLD_WRITEABLE).edit().putString(Preferences.USERNAME, emailAddress).commit();

                    Intent mIntent = new Intent();
                    mIntent.putExtras(extras);
                    setResult(RESULT_OK, mIntent);
                    finish();
                }

                //TODO: What to do when service cannot register user?
            }

        });
    }
}
