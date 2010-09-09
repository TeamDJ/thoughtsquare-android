package com.thoughtsquare.utility;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import com.thoughtsquare.intent.IntentActions;

public class SmsManagerWrapper {

    private SmsManager smsManager;
    private Context context;

    public SmsManagerWrapper(SmsManager smsManager, Context context) {
        this.smsManager = smsManager;
        this.context = context;
    }

     public void sendTextMessage(String destinationAddress, String scAddress, String text) {
         PendingIntent sentPI = PendingIntent.getBroadcast(context, 0,
            new Intent(IntentActions.SENT), 0);

            PendingIntent deliveredPI = PendingIntent.getBroadcast(context, 0,
                new Intent(IntentActions.DELIVERED), 0);
         smsManager.sendTextMessage(destinationAddress, scAddress, text, sentPI, deliveredPI);
     }
}
