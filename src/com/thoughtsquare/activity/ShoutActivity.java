package com.thoughtsquare.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.thoughtsquare.R;
import com.thoughtsquare.domain.Location;
import com.thoughtsquare.domain.User;
import com.thoughtsquare.domain.UserProvider;
import com.thoughtsquare.service.ShoutService;
import com.thoughtsquare.utility.ViewUtils;

import static com.thoughtsquare.utility.ViewUtils.setLabel;

public class ShoutActivity extends Activity {
    private ShoutService shoutService;
    private Location location;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shout);

        shoutService = new ShoutService();

        if(getIntent().getExtras() != null && getIntent().getExtras().get("location") !=null){
            location = (Location)getIntent().getExtras().get("location");
            setLabel(this, R.id.shoutLocation, location.getTitle());
        }

        //TODO I'd rather be doing location.broadcastSMS(message) in spirit of rich domain
        Button shoutButton = (Button) findViewById(R.id.shoutButton);
        shoutButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                //TODO require a location - will need to select one if user's current location is not known.
                if(location!=null){
                    shoutService.sendSMS(location.getId());
                }
                finish();
            }
        });
    }


}