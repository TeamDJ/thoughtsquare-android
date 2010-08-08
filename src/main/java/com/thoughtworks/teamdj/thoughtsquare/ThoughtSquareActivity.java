package com.thoughtworks.teamdj.thoughtsquare;

import android.app.Activity;
import android.os.Bundle;
import com.thoughtworks.teamdj.thoughtsquare.R;

public class ThoughtSquareActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}
