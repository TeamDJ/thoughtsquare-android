package com.thoughtsquare.utility;

import android.app.Activity;
import android.widget.EditText;
import android.widget.TextView;
import com.thoughtsquare.activity.ThoughtSquareActivity;

public class ViewUtils {

    public static String getTextFromTextBox(Activity activity, int viewId) {
        return ((EditText) activity.findViewById(viewId)).getText().toString();
    }

    public static void setTextInTextBox(Activity activity, int viewId, String text) {
        ((EditText) activity.findViewById(viewId)).setText(text);
    }
    
    public static void setLabel(Activity activity, int viewId, String text) {
        ((TextView) activity.findViewById(viewId)).setText(text);
    }


    public static String getLabel(Activity activity, int viewId) {
        return ((TextView)activity.findViewById(viewId)).getText() + "";
    }
}
