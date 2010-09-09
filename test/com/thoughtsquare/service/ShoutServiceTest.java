package com.thoughtsquare.service;

import android.app.PendingIntent;
import android.content.Context;
import android.telephony.SmsManager;
import com.thoughtsquare.domain.Friend;
import com.thoughtsquare.utility.SmsManagerWrapper;
import org.junit.Before;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ShoutServiceTest {
    private static final int LOCATION_ID = 5;
    private static final String SHOUT_MESSAGE = "shout message";
    private ShoutService shoutService;
    private LocationService locationService;
    private SmsManagerWrapper smsManager;
    private static final String MOBILE1 = "mobile1";
    private static final String MOBILE2 = "mobile2";

    @Before
    public void setup(){
        smsManager = mock(SmsManagerWrapper.class);
        Context context = mock(Context.class);
        locationService = mock(LocationService.class);

        shoutService = new ShoutService(locationService, smsManager, context);
    }

    @Test
    public void shouldSendSMSIfThereAreFriendsAtALocation(){
        Friend friend1 = mock(Friend.class);
        Friend friend2 = mock(Friend.class);
        when(friend1.getMobileNumber()).thenReturn(MOBILE1);
        when(friend2.getMobileNumber()).thenReturn(MOBILE2);
        when(locationService.listFriendsAtLocation(LOCATION_ID)).thenReturn(asList(friend1, friend2));

        shoutService.sendSMS(LOCATION_ID, SHOUT_MESSAGE);

        verify(smsManager).sendTextMessage(MOBILE1, null, SHOUT_MESSAGE);
        verify(smsManager).sendTextMessage(MOBILE2, null, SHOUT_MESSAGE);


    }
}
