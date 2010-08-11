package teamdj.thoughtsquare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static teamdj.thoughtsquare.Preferences.DEFAULT;
import static teamdj.thoughtsquare.Preferences.DISPLAY_NAME;
import static teamdj.thoughtsquare.Preferences.USERNAME;

public class RegisterActivity extends Activity
{



    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        final EditText displayNameField= (EditText) findViewById(R.id.displayNameField);
        final EditText usernameField = (EditText) findViewById(R.id.usernameField);

        Button registerButton = (Button) findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String displayName = displayNameField.getText().toString();
                String username= usernameField.getText().toString();

                Bundle extras = new Bundle();
                extras.putString("display", displayName);
                extras.putString("username", username);

                getSharedPreferences(DEFAULT,  MODE_WORLD_WRITEABLE ).edit().putString(DISPLAY_NAME, displayName).commit();
                getSharedPreferences(DEFAULT,  MODE_WORLD_WRITEABLE ).edit().putString(USERNAME, username).commit();

                Intent mIntent = new Intent();
                mIntent.putExtras(extras);
                setResult(RESULT_OK, mIntent);
                finish();
            }

        });
    }
}
