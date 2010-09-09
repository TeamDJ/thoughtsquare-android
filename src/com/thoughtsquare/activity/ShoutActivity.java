package com.thoughtsquare.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.thoughtsquare.R;
import com.thoughtsquare.async.BackgroundTask;
import com.thoughtsquare.domain.Location;
import com.thoughtsquare.intent.IntentActions;
import com.thoughtsquare.service.LocationService;
import com.thoughtsquare.service.ShoutService;
import com.thoughtsquare.utility.*;

import static com.thoughtsquare.utility.ViewUtils.getTextFromTextBox;
import static com.thoughtsquare.utility.ViewUtils.setLabel;

public class ShoutActivity extends Activity {
    private ShoutService shoutService;
    private Location location;
    ProgressDialog spinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shout);

        Config config = new ConfigLoader().getConfig(this);
        AHTTPClient httpClient = new AHTTPClient();
        LocationService locationService = new LocationService(config, httpClient);
        SmsManagerWrapper smsManagerWrapper = new SmsManagerWrapper(SmsManager.getDefault(), this);
        shoutService = new ShoutService(locationService, smsManagerWrapper, this);

        if(getIntent().getExtras() != null && getIntent().getExtras().get("location") !=null){
            location = (Location)getIntent().getExtras().get("location");
            setLabel(this, R.id.shoutLocation, location.getTitle());
        }

        //TODO I'd rather be doing location.broadcastSMS(message) in spirit of rich domain
        Button shoutButton = (Button) findViewById(R.id.shoutButton);
        shoutButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                spinner = new ProgressDialog(ShoutActivity.this);
                spinner.setMessage("Sending SMS...");
                if(location != null){
                    spinner.show();
                    shoutService.sendSMS(location.getId(), getTextFromTextBox(ShoutActivity.this,R.id.shoutMessage));
                    spinner.dismiss();
                }
            }

        });

        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {

                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        makeToast("SMS sent");
                        finish();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        String text = "Generic failure";
                        makeToast(text);
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        makeToast("No service");
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        makeToast("Null PDU");
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        makeToast("Radio off");
                        break;
                }
            }
        }, new IntentFilter(IntentActions.SENT));

        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        makeToast("SMS delivered");

                        break;
                    case Activity.RESULT_CANCELED:
                        makeToast("SMS not delivered");
                        break;
                }

            }
        }, new IntentFilter(IntentActions.DELIVERED));
    }

    private void makeToast(String text) {
        Toast.makeText(getBaseContext(), text,
                Toast.LENGTH_LONG).show();
    }


}