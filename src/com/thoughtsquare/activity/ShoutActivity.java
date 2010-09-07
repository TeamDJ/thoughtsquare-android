package com.thoughtsquare.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.thoughtsquare.R;
import com.thoughtsquare.domain.User;
import com.thoughtsquare.domain.UserProvider;
import com.thoughtsquare.utility.ViewUtils;

import static com.thoughtsquare.utility.ViewUtils.setLabel;

public class ShoutActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shout);

        String locationTitle = (String)getIntent().getExtras().get("locationTitle");
        setLabel(this, R.id.shoutLocation, locationTitle);

        setupShoutButton();
    }


    private void setupShoutButton() {
        Button shoutButton = (Button) findViewById(R.id.shoutButton);
        shoutButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

            }
        });
    }


}