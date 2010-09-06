package com.thoughtsquare.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import com.thoughtsquare.R;
import com.thoughtsquare.async.RegisterUserTask;
import com.thoughtsquare.domain.User;
import com.thoughtsquare.domain.UserProvider;
import com.thoughtsquare.utility.AHTTPClient;
import com.thoughtsquare.utility.Config;
import com.thoughtsquare.utility.ConfigLoader;

import java.util.concurrent.ExecutionException;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.thoughtsquare.utility.ViewUtils.getTextFromTextBox;
import static com.thoughtsquare.utility.ViewUtils.setTextInTextBox;

public class RegisterActivity extends Activity {
    private AsyncTask<User, Void, Boolean> registerUserTask;
    private String displayName;
    private String emailAddress;
    private String mobileNumber;
    private UserProvider userProvider;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        final Config config = new ConfigLoader().getConfig(this);
        userProvider = new UserProvider(getDefaultSharedPreferences(this), new AHTTPClient(), config);

        populateMobileNumber();

        setupRegisterButton();
    }

    private void populateMobileNumber() {
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String mobileNumber = telephonyManager.getLine1Number();

        if(mobileNumber!=null){
           setTextInTextBox(this, R.id.mobileNumber, mobileNumber);
        }
    }                       

    private void setupRegisterButton() {
        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                displayName = getTextFromTextBox(RegisterActivity.this, R.id.displayName);
                emailAddress = getTextFromTextBox(RegisterActivity.this, R.id.emailAddress);
                mobileNumber = getTextFromTextBox(RegisterActivity.this, R.id.mobileNumber);

                final User user = userProvider.createUser(emailAddress, displayName, mobileNumber);

                registerUserTask = new RegisterUserTask(RegisterActivity.this).execute(user);
            }

        });
    }

    public void onFinishRegisterTask() {
        boolean registerSuccess = false;
        try {
            registerSuccess = registerUserTask.get();
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);    //To change body of overridden methods use File | Settings | File Templates.
    }



  
}
