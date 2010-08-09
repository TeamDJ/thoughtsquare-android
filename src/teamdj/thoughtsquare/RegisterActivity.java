package teamdj.thoughtsquare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                Bundle bundle = new Bundle();

                String displayName = displayNameField.getText().toString();
                String username= usernameField.getText().toString();

                // do http post to create new user
                // save username if successful

                Intent mIntent = new Intent();
                setResult(RESULT_OK, mIntent);
                finish();
            }

        });
    }
}
