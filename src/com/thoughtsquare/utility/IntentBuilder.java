package com.thoughtsquare.utility;

import android.content.Intent;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

import static com.thoughtsquare.intent.IntentActions.LOCATION_UPDATED;

public class IntentBuilder {

    private String action;
    private Map<String, Parcelable> parcelableExtras = new HashMap<String, Parcelable>();

    public IntentBuilder withAction(String action) {
        this.action = action;
        return this;
    }

    public IntentBuilder withParcelable(String key, Parcelable value) {
        this.parcelableExtras.put(key, value);
        return this;
    }

    public Intent build() {
        Intent intent = new Intent();
        intent.setAction(action);
        for (Map.Entry<String, Parcelable> entry : parcelableExtras.entrySet()){
            intent.putExtra(entry.getKey(), entry.getValue());
        }
        return intent;
    }
}
