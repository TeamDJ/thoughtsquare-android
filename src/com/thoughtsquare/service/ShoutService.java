package com.thoughtsquare.service;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import com.thoughtsquare.domain.Friend;
import com.thoughtsquare.intent.IntentActions;
import com.thoughtsquare.utility.SmsManagerWrapper;

import java.util.List;

public class ShoutService {
    private LocationService locationService;
    private SmsManagerWrapper smsManager;
    private Context context;

    public ShoutService(LocationService locationService, SmsManagerWrapper smsManager, Context context) {
        this.locationService = locationService;
        this.smsManager = smsManager;
        this.context = context;
    }

    public void sendSMS(int locationId, String message) {
        List<Friend> friends = locationService.listFriendsAtLocation(locationId);
        for (Friend friend: friends){
            smsManager.sendTextMessage(friend.getMobileNumber(), null, message);
        }
        
    }
}
