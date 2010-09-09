package com.thoughtsquare.service;

import android.telephony.SmsManager;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ShoutServiceTest {
    private static final int LOCATION_ID = 5;
    private static final String SHOUT_MESSAGE = "shout message";

    @Test
    public void shouldSendSMS(){
        SmsManager smsManager = mock(SmsManager.class);
        ShoutService shoutService = new ShoutService(smsManager);

        shoutService.sendSMS(LOCATION_ID, SHOUT_MESSAGE);

        verify(smsManager).sendTextMessage("destination", null, SHOUT_MESSAGE, null, null);
    }
}
