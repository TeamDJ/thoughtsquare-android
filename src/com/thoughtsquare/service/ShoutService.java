package com.thoughtsquare.service;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import com.thoughtsquare.intent.IntentActions;

public class ShoutService {
    private SmsManager smsManager;
    private Context context;

    public ShoutService(SmsManager smsManager, Context context) {
        this.smsManager = smsManager;
        this.context = context;
    }

    public void sendSMS(int locationId, String message) {
        PendingIntent sentPI = PendingIntent.getBroadcast(context, 0,
            new Intent(IntentActions.SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(context, 0,
            new Intent(IntentActions.DELIVERED), 0);
        smsManager.sendTextMessage("0421757702", null, message, sentPI, deliveredPI);
        
    }
}
